/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $("#researchFilter").on("change keyup paste ", function () {
        var searchString = $(this).val().trim();

        $("#treeData ul li").each(function (index, grandLine) {

            if (searchString !== '') {
                if (($(this).hasClass('jstree-closed'))) {
                    $(this).children('i').click();
                }
            }

            if (searchString === '') {
                if ($(this).hasClass('jstree-open')) {
                    $(this).children('i').click();
                }
            }

            var find = false;

            if (searchString !== '') {
                $("li", this).each(function (index, lineValue) {
                    let searchValue = $(lineValue).text();
                    if (searchValue.toUpperCase().indexOf(searchString.toUpperCase()) > -1) {
                        $(this).show();
                        $(this).addClass("show-line").removeClass("hide-line");
                        find = true;
                    } else {
                        $(this).addClass("hide-line").removeClass("show-line");
                        $(this).hide();
                    }
                });
            } else {
                find = true;
            }

            if (find === false) {
                if (searchString !== '') {
                    $(this).hide();
                }
            } else {
                $(this).show();
            }
        });
    });

    $("#researchFilter").on("change keyup paste ", function () {
        var searchString = $(this).val().trim();

        $("#treeDataFirst").each(function (index, grandLine) {


            if (searchString !== '') {
                $("li", this).each(function (index, lineValue) {
                    let searchValue = $(lineValue).text();
                    if (searchValue.toUpperCase().indexOf(searchString.toUpperCase()) > -1) {
                        $(this).show();
                        $(this).addClass("show-line").removeClass("hide-line");
                        find = true;
                    } else {
                        $(this).addClass("hide-line").removeClass("show-line");
                        $(this).hide();
                    }
                });
            } else {
                $("li", this).each(function (index, lineValue) {
                    if ($(this).hasClass("hide-line")) {
                        $(this).addClass("show-line").removeClass("hide-line");
                    }
                });
            }


        });
    });
});
