/**
 * Theme: Ubold Admin Template
 * Author: Coderthemes
 * Tree view
 */

$(document).ready(function () {
    // Basic
    $('#basicTree').jstree({
        'core': {
            'themes': {
                'responsive': false
            }
        },
        'types': {
            'default': {
                'icon': 'md md-folder'
            },
            'file': {
                'icon': 'md md-insert-drive-file'
            }
        },
        'plugins': ['types', 'state', 'dnd']
    });
    
    // class de Basic tree
    $('.basicTree').jstree({
        'core': {
            'themes': {
                'responsive': false
            }
        },
        'types': {
            'default': {
                'icon': 'md md-folder'
            },
            'file': {
                'icon': 'md md-insert-drive-file'
            }
        },
        'plugins': ['types', 'state', 'dnd']
    });

    // Checkbox
    $('#checkTree').jstree({
        'core': {
            'themes': {
                'responsive': false
            }
        },
        'types': {
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        },
        'plugins': ['types', 'checkbox']
    });

    // Drag & Drop
    $('#dragTree').jstree({
        'core': {
            'check_callback': true,
            'themes': {
                'responsive': false
            }
        },
        'types': {
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        },
        'plugins': ['types', 'state', 'dnd']
    });

    $('.dragTree').jstree({
        'core': {
            'check_callback': true,
            'themes': {
                'responsive': false
            }
        },
        'types': {
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        },
        'plugins': ['types', 'dnd']
    });

    // Ajax
    $('#ajaxTree').jstree({
        'json_data': {
            'check_callback': true,
            'themes': {
                'responsive': false
            },
            'data': {
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return node.id === '#' ? url + 'api/ged/directory-node/content?id=1' : url + 'api/ged/directory-node/content?' + id;
                },
                'data': function (node) {
                    return {'id': node.id};
                },
                'type': 'get',
                'success': function (ops) {
                    data = [];
                    for (var i = 0; i < ops.directories.length; i++) {
                        node = {
                            "data": ops.directories[i].id,
                            "id": ops.directories[i].id,
                            "metadata": ops.directories[i],
                            "state": "closed"
                        }
                        data.push(node);
                    }
                    return data;
                }
            }
        },
        "types": {
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        },
        "plugins": ["types", "search", "types", "dnd"]
    });

    // Ajax
    $('#entite_old').jstree({
        'json_data': {
            'check_callback': true,
            'themes': {
                'responsive': false
            },
            'data': {
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return node.id === '#' ? url + 'api/parametre/refstransverses/entiteorganisationnelle/content?id=1' : url + 'api/parametre/refstransverses/entiteorganisationnelle/content?' + id;
                },
                'data': function (node) {
                    return {'id': node.id};
                },
                'type': 'get',
                'success': function (ops) {
                    data = [];
                    for (var i = 0; i < ops.directories.length; i++) {
                        node = {
                            "data": ops.directories[i].id,
                            "id": ops.directories[i].id,
                            "metadata": ops.directories[i],
                            "state": "closed"
                        }
                        data.push(node);
                    }
                    return data;
                }
            }
        },
        "types": {
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        },
        "plugins": ["types", "search", "types", "dnd"]
    });

    //Common Ajax JSTree
    $("[id^='jstree_demo_item_']").jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#1').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
            "dnd", "search", "types",
        ]
    });

//Personalized ajax tree
    $('#jstree_final').jstree({
        "core": {
            "animation": 3,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': function (node, cb) {
                if (node.id === "#") {
                    console.log("gettings node");
                    var root = getData(node);
                    console.log("got node");
                    console.log(root);
                    cb.call(this, root);
                } else {
                    var root_a = getData(node);
                    console.log("root_child");
                    console.log(root_a);
                    cb.call(this, root_a);
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                "icon": "/static/3.3.5/assets/images/tree_icon.png",
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
            "contextmenu", "dnd", "search", "types", "state"
        ]
    });

    function getData(node) {
        var url = $('#marbre_noeud').attr('href');
        var id = $('#marbre_noeud').attr('data-id');
        console.log("ID = " + node.id);

        var JSONdata = [];
        $.ajax({
            url: url + 'api/ged/directory-node/content?id=' + node.id,
            contentType: 'json',
            type: 'GET',
            dataType: 'JSON',
            success: function (ops, status, jqXHR) {
                data = [];
                console.log("Length " + ops.directories.length);
                for (var i = 0; i < ops.directories.length; i++) {
                    node = {
                        "data": ops.directories[i].id,
                        "id": ops.directories[i].id,
                        "text": ops.directories[i].name,
                        "metadata": ops.directories[i],
                        "state": "closed",
                        "children": true
                    }
                    data.push(node);
                }
                console.log("Data " + JSON.stringify(data));
                data.push({
                    'text': 'Root node 2',
                    'state': {
                        'opened': false,
                        'selected': true
                    },
                    'children': [
                        {'text': 'Child 1'},
                        'Child 2'
                    ]
                });
//                $('#jstree_final').jstree("refresh");
                return data;
            },
            error: function (jqXHR, status, errorThrown) {
                //errorHandler(errorThrown, status, jqXHR, context);
                console.log(jqXHR);
                console.log(status);
                console.log(errorThrown);
            }
            //xhrFields: {'withCredentials': true}, crossDomain: true
        });
        console.log(JSONdata);
        return JSONdata;
    }

    $('#visualDoss').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#1').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
            "dnd", "search", "types",
        ]
    });




    $('#jstree_demo').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#1').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
            "dnd", "search", "types",
        ]
    });

    $('#entite').jstree({
        "core": {
            'multiple': false,
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    console.log(url + 'api/parametre/refstransverses/entiteorganisationnelle/contenu');
                    return url + 'api/parametre/refstransverses/entiteorganisationnelle/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
             "search", "types"
        ]
    });
    
    $('#lignebudtree').jstree({
        "core": {
            'multiple': false,
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    console.log(url + 'api/parameter/ref/budgetaire/encodage/trees');
                    return url + 'api/parameter/ref/budgetaire/encodage/trees';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
             "search", "types"
        ]
    });

    $('#entiteactive').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    return url + 'api/parametre/refstransverses/entiteorganisationnelle/contenuActif';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"],
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        "plugins": [
            "dnd", "search", "types",
        ]
    });

    function generateTree(elt) {
        console.log(elt);
    }

    $('#jstree_search_checbox1').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        'plugins': ["types", "checkbox", "dnd"]
    });
    $('#jstree_visualisation').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        'plugins': ["types", "dnd"]
    });
    $('#jstree_search_checbox').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return url + 'api/ged/directory-node/contenu';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        'plugins': ["types", "checkbox", "dnd"]
    });

    $('#jstree_search_checkbox_with_path').jstree({
        "core": {
            "animation": 0,
            "check_callback": true,
            "themes": {"stripes": true},
            'data': {
                'dataType': "json",
                'url': function (node) {
                    var url = $('#marbre_noeud').attr('href');
                    var id = $('#marbre_noeud').attr('data-id');
                    return url + 'api/ged/directory-node/contenuPath';
                },
                'data': function (node) {
                    return {'id': node.id};
                }
            }
        },
        "types": {
            "#": {
                "max_children": 3,
                "max_depth": 4,
                "valid_children": ["default"]
            },
            "root": {
                'icon': 'fa fa-folder',
                "valid_children": ["default"]
            },
            'default': {
                'icon': 'fa fa-folder'
            },
            'file': {
                'icon': 'fa fa-file'
            }
        }, "html_titles": true, "load_open": true,
        'plugins': ['types', 'checkbox', 'dnd']
    });

});
