/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

'use strict';
/*
 * Generic basic services
 */
var App;
App.factory('GenericService', ['$http', '$q', function ($http, $q) {
        return {
            /*
             * Get method
             */
            get: function (url, param) {

                showSpinner();
                var deferred = $q.defer();
                $http.get(url)
                        .then(
                                function (response) {
                                    // notification('success', 'Opération effectuée avec succes');
                                    //displayMessages(response.data);
                                    deferred.resolve(response.data);
                                    hideSpinner();
                                },
                                function (errResponse) {
                                    if (param == undefined) {
                                        if (errResponse.status && errResponse.status === 401) {
                                            document.location.href = appUrl + "login";
                                        }
                                        if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
//                                        console.log(errResponse.data);
                                            notification('error', errResponse.data.message)
                                            //$.Notification.notify('error', 'top right', errResponse.data.message);
                                        } else
                                        if (errResponse.data && errResponse.data.reasonPhrase) {
                                            console.info();
                                            notification('error', errResponse.data.reasonPhrase);
                                            //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                        } else {
                                            //$.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                            notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                        }
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            getCustomize: function (url) {
                showSpinner();
                var deferred = $q.defer();
                $http.get(url)
                        .then(
                                function (response) {
                                    notification('success', 'Opération effectuée avec succes');
                                    //displayMessages(response.data);
                                    deferred.resolve(response.data);
                                    hideSpinner();
                                },
                                function (errResponse) {
                                    if (errResponse.status && errResponse.status === 401) {
//                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
//                                        console.log(errResponse.data);
                                        //$.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message);
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
                                        //$.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            getOnly: function (url) {
//                showSpinner();
                var deferred = $q.defer();
                $http.get(url)
                        .then(
                                function (response) {
                                    notification('success', 'Opération effectuée avec succes');
                                    //displayMessages(response.data);
                                    deferred.resolve(response.data);
//                                    hideSpinner();
                                },
                                function (errResponse) {
                                    if (errResponse.status && errResponse.status === 401) {
//                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
//                                        console.log(errResponse.data);
//                                        $.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message);
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
//                                        $.Notification.notify('error', 'top right', 'Error lors de l\'envoi de requête ! ', '');
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                    }
//                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            /**
             * 
             */
            getWithHeader: function (url, config) {
                showSpinner();
                var deferred = $q.defer();
                $http.get(url, config)
                        .then(
                                function (response) {
                                    notification('success', 'Opération effectuée avec succes');
//                                    displayMessages(response.data);
                                    deferred.resolve(response.data);
                                    hideSpinner();
                                },
                                function (errResponse) {
                                    if (errResponse.status && errResponse.status === 401) {
                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
//                                        console.log(errResponse.data);
                                        //$.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message);
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                        //$.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            /*
             * Post method
             */
            post: function (url, data, responseType) {
                showSpinner();
                var deferred = $q.defer();
                $http.post(url, data, responseType)
                        .then(
                                function (response) {
                                    notification('success', 'Opération effectuée avec succes');
                                    //displayMessages(response.data);
                                    hideSpinner();
                                    deferred.resolve(response.data);
                                },
                                function (errResponse) {
                                    console.info(responseType);
                                    if (responseType && responseType.responseType == 'arraybuffer') {


                                        if ('TextDecoder' in window) {
                                            // Decode as UTF-8
                                            var dataView = new DataView(errResponse.data);
                                            var decoder = new TextDecoder('utf8');
                                            errResponse.data = JSON.parse(decoder.decode(dataView));
                                        } else {
                                            // Fallback decode as ASCII
                                            var decodedString = String.fromCharCode.apply(null, new Uint8Array(errResponse.data));
                                            errResponse.data = JSON.parse(decodedString);
                                        }


                                        console.log(errResponse);
                                    }
                                    if (errResponse.status && errResponse.status == 401) {
                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
                                        //$.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message);
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        // $.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                        // $.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            /*
             * Update methode
             */
            put: function (url) {
                showSpinner();
                var deferred = $q.defer();
                $http.put(url)
                        .then(
                                function (response) {
                                    notification('success', 'Opération effectuée avec succes');
                                    //displayMessages(response.data);
                                    hideSpinner();
                                    deferred.resolve(response.data);
                                },
                                function (errResponse) {
                                    if (errResponse.status && errResponse.status == 401) {
                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
//                                        $.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message)
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                        //$.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }
                        );
                return deferred.promise;
            },
            /*
             * Delete methode
             */
            delete: function (url, data) {
                showSpinner();
                var deferred = $q.defer();
                $http.delete(url, data)
                        .then(
                                function (response) {
                                    notification('success', 'Supression effectuée avec succes')
                                    //displayMessages(response.data);
                                    deferred.resolve(response.data);
                                    hideSpinner();
                                },
                                function (errResponse) {
                                    if (errResponse.status && errResponse.status == 401) {
                                        document.location.href = appUrl + "login";
                                    }
                                    if (errResponse.status && errResponse.status === 400 && errResponse.data && !(errResponse.data.reasonPhrase)) {
                                        //$.Notification.notify('error', 'top right', errResponse.data.message);
                                        notification('error', errResponse.data.message);
                                    } else
                                    if (errResponse.data && errResponse.data.reasonPhrase) {
                                        console.info();
                                        notification('error', errResponse.data.reasonPhrase);
                                        //$.Notification.notify('error', 'top right', errResponse.data.reasonPhrase, '');
                                    } else {
                                        notification('error', 'Erreur lors de l\'envoi de requête ! ');
                                        //$.Notification.notify('error', 'top right', 'Erreur lors de l\'envoi de requête ! ', '');
                                    }
                                    hideSpinner();
                                    deferred.reject(errResponse);
                                }

                        );
                return deferred.promise;
            },
            showConfirmation1: function (complement, succes_callBack) {
                alert("Sure");
                swal.fire({
                    title: 'Are you sure?',
                    text: "You won't be able to revert this!",
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Yes, delete it!',
                    cancelButtonText: '&lt;button &gt;&lt;/button&gt;',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger m-l-10',
                    reverseButtons: true
                }).then((result) => {
                    if (result.value) {
                        swal.fire(
                                'Deleted!',
                                'Your file has been deleted.',
                                'success'
                                )
                    } else if (
                            // Read more about handling dismissals
                            result.dismiss === Swal.DismissReason.cancel
                            ) {
                        swal.fire(
                                'Cancelled',
                                'Your imaginary file is safe :)',
                                'error'
                                )
                    }
                })

            },
            showConfirmation: function (complement, succes_callBack) {
                swal({
                    title: 'Êtes-vous sûr ' + complement + ' ?',
                    text: '',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'Oui',
                    cancelButtonText: 'Non',
                    confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger m-l-10',
                    buttonsStyling: false
                }).then(function () {
                    succes_callBack();
                }, function (dismiss) {
                    if (dismiss === 'cancel') {
                        swal(
                                'Annulé',
                                "L'opération a été annulée",
                                'error'
                                )
                    }
                });
            },
            /**
             * 
             * @param {type} typeError
             * @param {type} message
             * @param {type} principalMessage
             * @returns {undefined}
             */
            frontValidationMessages: function (typeError, message, principalMessage) {
                let msgDetail = '';
                if (message) {
                    var type;
                    if (typeError === "DANGER") {
                        type = 'error';
                    } else if (typeError === "INFO") {
                        type = 'success';
                    } else if (typeError === "WARNING") {
                        type = 'warning';
                    } else {
                        type = 'warning';
                    }

                    $.Notification.notify(type, 'top right', principalMessage, function () {
                        msgDetail += "<ul>";
                        msgDetail = msgDetail + "<li> " + message + "</li>";
                        msgDetail += "</ul>";
                        return msgDetail;
                    });
                }
            }
        };
        function displayMessages(data) {
            let msgDetail = '';
            if (data.msg) {
                var type;
                if (data.msg.typeError === "DANGER") {
                    type = 'error';
                } else if (data.msg.typeError === "INFO") {
                    type = 'success';
                } else if (data.msg.typeError === "WARNING") {
                    type = 'warning';
                } else {
                    type = 'warning';
                }

                $.Notification.notify(type, 'top right', data.msg.principal, function () {
                    if (data.msg.errorMessages) {
                        msgDetail += "<ul>";
                        for (var i = 0; i < data.msg.errorMessages.length; i++) {
                            msgDetail = msgDetail + "<li> " + data.msg.errorMessages[i].message + "</li>";
                        }
                        msgDetail += "</ul>";
                    }
                    return msgDetail;
                });
            }
        }
        ;
        function notification(icon, title) {
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                onOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            });
            Toast.fire({
                icon: icon,
                title: title
            });
        }
        ;
    }]);

