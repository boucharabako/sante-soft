$(document).ready(function () {
    //    Activation du loading spinner
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