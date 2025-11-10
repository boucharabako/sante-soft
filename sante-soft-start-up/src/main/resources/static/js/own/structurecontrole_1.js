/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function showDetail(event) {
    window.all_Zone_Input_array = [];
    window.all_Sous_Zone_Input_array = [];
    var showBtn = document.getElementById('aper');
    showBtn.style.visibility = "hidden";
    var url = $(event).attr('href');
    var previewDiv = document.getElementById('previewDiv');
    previewDiv.innerHTML = '';
    window.globalvariable = "";
    var sousZoneH = document.getElementById("previewLabel");
    sousZoneH.innerHTML = '';
    window.indexz;
    var sousZoneH = document.createElement("H1");
    sousZoneH.setAttribute("id", "idAllSousZoneH");
    previewDiv.appendChild(sousZoneH);
    doGet((url),
            function (data) {
                if (data.listZone.length !== 0) {
                    showBtn.style.visibility = "visible";
                    data.listZone.forEach(function (zone) {
                        window.indexz = data.listZone.indexOf(zone);
                        console.log("********* "+window.indexz);
                        console.log("-------|||||||-- "+data.listZone.length);
                        if (zone.listSousZone.length === 0) {
                            console.log("PAS SOUS DE ZONE");
                            // Recherche l'index de la sous zone courante
                            var index = data.listZone.indexOf(zone);
                            
                            
                            // Creation des inputs
                            var textField = document.createElement("INPUT");
                            // ajout de la class form-control
                            textField.setAttribute("class", "form-control");
                            // Affectation de l'ID
                            textField.setAttribute("id", zone.id);

                            window.all_Zone_Input_array.push(zone);
                            //Placeholder
                            textField.setAttribute("placeholder", zone.intitule);

                            // Definition du type des inputs
                            if (zone.typeDeDonneeString === "N") {
                                textField.setAttribute("type", "number");
                                textField.setAttribute("min", "0");
                                textField.setAttribute("max", getMaxNumber(zone.tailleZone + (zone.separateur.length)));
                                textField.setAttribute("title", "Veuillez respecter le format! seul les valeurs numériques sont acceptées");
                            }
                            if (zone.typeDeDonneeString === "X") {
                                textField.setAttribute("type", "text");
                                textField.setAttribute("pattern", "[a-zA-Z0-9]+");
                                textField.setAttribute("title", "Veuillez respecter le format");
                            }
                            if (zone.typeDeDonneeString === "A") {
                                textField.setAttribute("type", "text");
                                textField.setAttribute("pattern", "[A-Za-z]{1," + zone.tailleZone + (zone.separateur.length) + "}");
                                textField.setAttribute("title", "Veuillez respecter le format! seul les valeurs alphabétiques sont acceptées");
                            }
                            //definir la taille de la zone au cas ou c'est de type texte
                            textField.setAttribute("maxlength", zone.tailleZone + (zone.separateur.length));

                            // definir le champs comme obligatoire
                            textField.setAttribute("required", "true");
                            textField.style.width = "150px";

                            var formGroupDiv = document.createElement("DIV");
                            formGroupDiv.setAttribute("class", "form-group col-lg-2");
                            var label = document.createElement("LABEL");
                            label.setAttribute("class", "control-label");
                            var labelText = document.createTextNode(zone.intitule + " :");
                            label.appendChild(labelText);
                            formGroupDiv.appendChild(label);

                            var inputDiv = document.createElement("DIV");
                            inputDiv.appendChild(textField);

                            formGroupDiv.appendChild(inputDiv);

                            // Ajout des inputs au Div
                            previewDiv.appendChild(formGroupDiv);

                            var spanDiv = document.createElement("DIV");
                            spanDiv.style.padding = "4% 0% 0px 0px";
                            if (index !== data.listZone.length - 1) {
                                var span = document.createElement("SPAN");
                                var spanText = document.createTextNode(zone.separateur);
                                span.appendChild(spanText);
                                // Ajout des séparateurs au Div
                                spanDiv.appendChild(span);
                            }

                            previewDiv.appendChild(spanDiv);

                        } else {
                            // La zone a des sous zones
                            zone.listSousZone.forEach(function (sousZone) {
                                var index = zone.listSousZone.indexOf(sousZone);
                                // Creation des inputs
                                var textField = document.createElement("INPUT");
                                // ajout de la class form-control
                                textField.setAttribute("class", "form-control");
                                //Placeholder
                                textField.setAttribute("placeholder", sousZone.intitule);
                                // Affectation de l'ID
                                textField.setAttribute("id", sousZone.id);
                                window.all_Sous_Zone_Input_array.push(sousZone);

                                // Definition du type des inputs
                                textField.setAttribute("maxlength", sousZone.tailleSousZone);
                                // Creation des pattern en fonction du type de donnée des inputs
                                if (sousZone.typeDeDonneeString === "N") {
                                    textField.setAttribute("type", "number");
                                    textField.setAttribute("min", "0");
                                    textField.setAttribute("max", getMaxNumber(sousZone.tailleSousZone));
                                    textField.setAttribute("title", "Veuillez respecter le format ce nombre ne doit pas depasser" + sousZone.tailleSousZone);
                                }
                                if (sousZone.typeDeDonneeString === "X") {
                                    textField.setAttribute("type", "text");
                                    textField.setAttribute("pattern", "[a-zA-Z0-9]+");
                                    textField.setAttribute("title", "Veuillez respecter le format");
                                }
                                if (sousZone.typeDeDonneeString === "A") {

                                    textField.setAttribute("type", "text");
                                    textField.setAttribute("pattern", "[A-Za-z]{1," + sousZone.tailleSousZone + "}");
                                    textField.setAttribute("title", "Veuillez respecter le format! seul les valeurs alphabtiques sont acceptées");
                                }

                                textField.setAttribute("required", "true");
                                textField.style.width = "150px";

                                var formGroupDiv = document.createElement("DIV");
                                formGroupDiv.setAttribute("class", "form-group col-lg-2");

                                var label = document.createElement("LABEL");
                                label.setAttribute("class", "control-label");
                                var labelText = document.createTextNode(sousZone.intitule + " :");
                                label.appendChild(labelText);
                                formGroupDiv.appendChild(label);

                                var inputDiv = document.createElement("DIV");
                                inputDiv.appendChild(textField);

                                formGroupDiv.appendChild(inputDiv);

                                // Ajout des inputs au Div
                                previewDiv.appendChild(formGroupDiv);

                                // ajout de separateur au apres la derniere sous zone d'une zone
                                var spanDiv = document.createElement("DIV");
                                spanDiv.style.padding = "4% 0% 0px 0px";
                                console.log("-index- "+window.indexz);
                                console.log("-------taile-- "+data.listZone.length);
                                if (index === zone.listSousZone.length - 1 && window.indexz === data.listZone.length) {
                                    var span = document.createElement("SPAN");
                                    var spanText = document.createTextNode(zone.separateur);
                                    window.globalvariable = zone.separateur;
                                    span.appendChild(spanText);
                                    // Ajout des séparateurs au Div
                                    spanDiv.appendChild(span);
                                }

                                previewDiv.appendChild(spanDiv);

                            });
                            
                            
                            
                            
                            
                            
                        }
                    });
                }
                $("#form_id input").on("keyup change", function () {

                    let elt = $(this);
                    let mx = parseInt(elt.attr("max"));
                    let valeur = parseInt(elt.val());

                    console.log("avant control max:" + mx + " valeur:" + valeur + " " + JSON.stringify(elt));
                    console.log(mx === valeur);

                    if (valeur > mx) {
                        elt.val(mx);
                        console.log("max:" + mx + " valeur:" + valeur + " " + JSON.stringify(elt));
                    }
                });
            },
            function (error) {
                console.log("Erreur " + error);
            });

    function getMaxNumber(taille) {
        var number = (Math.pow(10, taille) - 1);
        return number;
    }
}

function submitValue() {
    $('#form_id').submit(function () {
        showAllDetail();
        return false;
    });
}

function showAllDetail() {
    var sousZoneH = document.getElementById("previewLabel");
    sousZoneH.innerHTML = '';
    var sousZoneAllValue = "";
    var zoneAllValue = "";

    //For Zone
    window.all_Zone_Input_array.forEach(function (element) {
        zoneAllValue += document.getElementById(element.id).value + element.separateur;
    });
    //For Sous Zone
    window.all_Sous_Zone_Input_array.forEach(function (element) {
        var index = window.all_Sous_Zone_Input_array.indexOf(element);
        if (index === window.all_Sous_Zone_Input_array.length - 1) {
            sousZoneAllValue += document.getElementById(element.id).value + window.globalvariable;
        } else {
            sousZoneAllValue += document.getElementById(element.id).value;
        }
    });

    var zz = sousZoneAllValue + zoneAllValue;
    zz = zz.slice(0, -1);

    var t = document.createTextNode(zz);
    sousZoneH.appendChild(t);
}

//if(!zn.getTypeDeDonnee().getIdeCode().equals(sousZoneToSave.getTypeDeDonnee().getIdeCode())){
//                    System.out.println("------------non respecté");
//                }

