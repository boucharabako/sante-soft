$(window).bind("load", function () {
    console.log("visualisation window open");
    $(".names").each(function () {
        let urle = "" + $(this).attr("href");
        let current = $(this);
        $.ajax({
            url: urle,
            type: "GET",
            success: function (data, textStatus, jqXHR) {
                current.html(data.value);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("error:" + JSON.stringify(errorThrown));
            }
        });
    });
});