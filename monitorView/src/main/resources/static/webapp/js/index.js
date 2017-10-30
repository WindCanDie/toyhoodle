var netPath = "https://127.0.0.1:8443/"

function queryList() {
    $.ajax({
        url: netPath + "test/hello",
        context: document.body,
        success: function (m) {
            console.log(m)
        }
    })
}