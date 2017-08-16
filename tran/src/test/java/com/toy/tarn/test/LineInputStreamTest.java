package com.toy.tarn.test;

import com.toy.tran.data.extract.file.LineInputStream;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/4.
 */
public class LineInputStreamTest {
    @Test
    public void LineInputReadTest() throws IOException {
        LineInputStream lineInputStream = new LineInputStream(new FileInputStream(new File("G:\\java\\toy\\toyhoodle\\tran\\src\\test\\resources\\LineInputStream.txt")), "<>", "UTF-8");
        String v;
        while ((v = lineInputStream.readCustomLine(64 * 1024, 64 * 1024)) != null) {
            System.out.println(v);
        }
    }

    @Test
    public void ByteCharTest() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        byteBuffer.putInt(1);
    }

    protected int fillBuffer(InputStream in, byte[] buffer, boolean inDelimiter)
            throws IOException {
        return in.read(buffer);
    }

    @Test
    public void Atest() throws IOException {
        int i = readCustomLine(new Text(), 64 * 1024, 64 * 1024);
        System.out.print(i);
    }

    private int readCustomLine(Text str, int maxLineLength, int maxBytesToConsume)
            throws IOException {
        int bufferPosn = 0;
        int bufferLength = 0;
        byte[] recordDelimiterBytes = "<>".getBytes();
        FileInputStream in = new FileInputStream(new File("G:\\java\\toy\\toyhoodle\\tran\\src\\test\\resources\\LineInputStream.txt"));

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
        byte[] buffer = new byte[64 * 1024];
        str.clear();
        int txtLength = 0; // tracks str.getLength(), as an optimization
        long bytesConsumed = 0;
        int delPosn = 0;
        int ambiguousByteCount = 0; // To capture the ambiguous characters count
        do {
            int startPosn = bufferPosn; // Start from previous end position
            if (bufferPosn >= bufferLength) {
                startPosn = bufferPosn = 0;
                bufferLength = fillBuffer(in, buffer, ambiguousByteCount > 0);
                if (bufferLength <= 0) {
                    if (ambiguousByteCount > 0) {
                        str.append(recordDelimiterBytes, 0, ambiguousByteCount);
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
                str.append(recordDelimiterBytes, 0, ambiguousByteCount);
                ambiguousByteCount = 0;
                // since it is now certain that the split did not split a delimiter we
                // should not read the next record: clear the flag otherwise duplicate
                // records could be generated
                //        unsetNeedAdditionalRecordAfterSplit();
            }
            if (appendLength > 0) {
                str.append(buffer, startPosn, appendLength);
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
        return (int) bytesConsumed;
    }
}
