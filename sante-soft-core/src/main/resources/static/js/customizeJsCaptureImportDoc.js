$("#jstree_demo").on("select_node.jstree",
        function (evt, data) {
             console.log("select_node");
            $('input#dossier').val(data.node.id);
        }
);
$("#jstree_demo").on("deselect_node.jstree",
        function (evt, data) {
            console.log("unSelect_node");
            $('input#dossier').val("");
        }
);

$(document).ready(function () {
//    Activation du loading spinner
    triggerSpinner();

    $("#file_input").on('change', function () {
        $("#btn_send_files").click();
    });
});