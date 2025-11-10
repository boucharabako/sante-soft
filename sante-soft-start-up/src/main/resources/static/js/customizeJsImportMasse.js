$("#file_input").on('change', function () {
    $("#hidden_mulipart").val($("#folder_input").val());
    $("#btn_send_files").click();
});
$("#folder_input").on('change', function () {
    $("#hidden_mulipart").val($("#folder_input").val());
    $("#btn_send_folder").click();
});
$('#checkbox6b').on('change', function () {
    $("#btn_folder").click();
});

function checkForm() {
    var tab = $(".sai_utilisateur");
    console.log(tab);
    for (var i = 0; i < tab.length; i++) {
        console.log(tab[i].val());
        //console.log($(tab[i]).val());
    }
}


$(document).ready(function () {
    $("#folder_input").attr('webkitdirectory', '');
    $("#folder_input").attr('mozdirectory', '');
    $("#folder_input").attr('msdirectory', '');
    $("#folder_input").attr('odirectory', '');
    $("#folder_input").attr('directory', '');
    $("#folder_input").val($("#hidden_mulipart").val());
    $("#file_input").val($("#hidden_mulipart").val());

    //Activation du loading spinner
    triggerSpinner();

    var owl = $("#owl-multi");



    owl.owlCarousel({
        loop: false,
//                        margin: 5,
        nav: true,
        navText: ['<i class="ion-chevron-left"></i>', '<i class="ion-chevron-right"></i>'],
        autoplay: false,
        responsive: {
            0: {
                items: 1
            },
            480: {
                items: 1
            },
            700: {
                items: 1
            },
            1000: {
                items: 2
            },
            1100: {
                items: 3
            }
        }
    });

});