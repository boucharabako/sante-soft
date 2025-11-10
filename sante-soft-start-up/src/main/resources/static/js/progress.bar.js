
function showSpinner() {
    $.preloader.start({
        modal: true,
        position: 'center',
        src: 'sprites.png'
    });
}

function hideSpinner() {
    $.preloader.stop();
}
function triggerSpinner() {
    $('.processing').click(function () {
        console.log("spinner loading");
        showSpinner();
    });
    
    $('form').submit(function () {
        console.log("submit spinner loading");
        showSpinner();
    });
}
function notify(style,position, title) {
        var icon = "fa fa-adjust";
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "custom"){
            icon = "md md-album";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }
        $.notify({
            title: title,
            text: 'Are you sure you want to do nothing?<div class="clearfix"></div><br><a class="btn btn-sm btn-white yes">Yes</a> <a class="btn btn-sm btn-danger no">No</a>',
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition:position,
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHide: false,
            clickToHide: false
        });
        //listen for click events from this style
        $(document).on('click', '.notifyjs-metro-base .no', function() {
          //programmatically trigger propogating hide event
          $(this).trigger('notify-hide');
        });
        $(document).on('click', '.notifyjs-metro-base .yes', function() {
          //show button text
          alert($(this).text() + " clicked!");
          //hide notification
          $(this).trigger('notify-hide');
        });
    }
