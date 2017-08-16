package com.toy.tran.data.extract.file;

import java.io.*;

/**
 * Created by Administrator on
 * 2017/8/3.
 */
public class LineInputStream {
    private final byte[] recordDelimiterBytes;
    private final String fileEncod;
    private int bufferPosn = 0;
    private int bufferLength = 0;
    private byte[] buffer = new byte[64 * 1024];
    private int maxLineLength = 10 * 1024;
    private int maxBytesToConsume = 100 * 1024;
    private InputStream in;

    /**
     * @return file END return null
     */


    public LineInputStream(String dir, String fileLineSplit, String fileEncod) throws FileNotFoundException {
        in = new FileInputStream(new File(dir));
        recordDelimiterBytes = fileLineSplit.getBytes();
        this.fileEncod = fileEncod;
    }

    private int fillBuffer(InputStream in, byte[] buffer)
            throws IOException {
        return in.read(buffer);
    }


    public String readCustomLine(int maxLineLength, int maxBytesToConsume)
            throws IOException {
   /* We're reading data from inputStream, but the head of the stream may be
    *  already captured in the previous buffer, so we have several cases:
    *
    * 1. The buffer tail does not contain any character sequence which
    *    matches with the head of delimiter. We count it as a
    *    ambiguous byte count = 0
    *
    * 2. The buffer tail contains a X number of characters,
    *    that forms a sequence, which matches with the
    *    head of delimiter. We count ambiguous byte count = X
    *
    *    // ***  eg: A segment of input file is as follows
    *
    *    " record 1792: I found this bug very interesting and
    *     I have completely read about it. record 1793: This bug
    *     can be solved easily record 1794: This ."
    *
    *    delimiter = "record";
    *
    *    supposing:- String at the end of buffer =
    *    "I found this bug very interesting and I have completely re"
    *    There for next buffer = "ad about it. record 179       ...."
    *
    *     The matching characters in the input
    *     buffer tail and delimiter head = "re"
    *     Therefore, ambiguous byte count = 2 ****   //
    *
    *     2.1 If the following bytes are the remaining characters of
    *         the delimiter, then we have to capture only up to the starting
    *         position of delimiter. That means, we need not include the
    *         ambiguous characters in str.
    *
    *     2.2 If the following bytes are not the remaining characters of
    *         the delimiter ( as mentioned in the example ),
    *         then we have to include the ambiguous characters in str.
    */
        StringBuffer str = new StringBuffer();
        int txtLength = 0; // tracks str.getLength(), as an optimization
        long bytesConsumed = 0;
        int delPosn = 0;
        int ambiguousByteCount = 0; // To capture the ambiguous characters count
        do {
            int startPosn = bufferPosn; // Start from previous end position
            if (bufferPosn >= bufferLength) {
                startPosn = bufferPosn = 0;
                bufferLength = fillBuffer(in, buffer);
                if (bufferLength <= 0) {
                    if (ambiguousByteCount > 0) {
                        str.append(new String(recordDelimiterBytes, 0, ambiguousByteCount, fileEncod));
                        bytesConsumed += ambiguousByteCount;
                    }
                    break; // EOF
                }
            }
            for (; bufferPosn < bufferLength; ++bufferPosn) {
                if (buffer[bufferPosn] == recordDelimiterBytes[delPosn]) {
                    delPosn++;
                    if (delPosn >= recordDelimiterBytes.length) {
                        bufferPosn++;
                        break;
                    }
                } else if (delPosn != 0) {
                    bufferPosn -= delPosn;
                    if (bufferPosn < -1) {
                        bufferPosn = -1;
                    }
                    delPosn = 0;
                }
            }
            int readLength = bufferPosn - startPosn;
            bytesConsumed += readLength;
            int appendLength = readLength - delPosn;
            if (appendLength > maxLineLength - txtLength) {
                appendLength = maxLineLength - txtLength;
            }
            bytesConsumed += ambiguousByteCount;
            if (appendLength >= 0 && ambiguousByteCount > 0) {
                //appending the ambiguous characters (refer case 2.2)
                str.append(new String(recordDelimiterBytes, 0, ambiguousByteCount, fileEncod));
                ambiguousByteCount = 0;
                // since it is now certain that the split did not split a delimiter we
                // should not read the next record: clear the flag otherwise duplicate
                // records could be generated
                //        unsetNeedAdditionalRecordAfterSplit();
            }
            if (appendLength > 0) {
                str.append(new String(buffer, startPosn, appendLength, fileEncod));
                txtLength += appendLength;
            }
            if (bufferPosn >= bufferLength) {
                if (delPosn > 0 && delPosn < recordDelimiterBytes.length) {
                    ambiguousByteCount = delPosn;
                    bytesConsumed -= ambiguousByteCount; //to be consumed in next
                }
            }
        } while (delPosn < recordDelimiterBytes.length
                && bytesConsumed < maxBytesToConsume);
        if (bytesConsumed > Integer.MAX_VALUE) {
            throw new IOException("Too many bytes before delimiter: " + bytesConsumed);
        }
        return bytesConsumed == 0 ? null : str.toString();
    }


    public String readData() throws IOException {
        return readCustomLine(maxLineLength, maxBytesToConsume);
    }

    public void close() throws IOException {
        in.close();
    }

}
