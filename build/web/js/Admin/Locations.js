/**
 * Created by johnn on 4/23/2017.
 */

var allLocations ="";
var activeList = "";
var page ="";
var addMap;
var marker;

function adminLocMap(){
    var mapOptions = {
        center: new google.maps.LatLng(44.580207622, -103.461760283),
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    addMap = new google.maps.Map(document.getElementById("googleAdminLocations"),mapOptions);
}

function placeLocationMarker(latt,longg){
    var make = new google.maps.LatLng(latt,longg);
    if(marker==null){
        marker = new google.maps.Marker({
            position:make,
            map:addMap,
            animation: google.maps.Animation.DROP,
            icon: "img/airport.png"
        });
    }
    marker.setPosition(make);
    addMap.panTo(make);
}

$(function(){

   $.post("General.ListLocations","activity=0",function(msg){
       if(msg.length>0){
           allLocations = JSON.parse(msg).states;
           setUpAdminStates("stateName","cityName");
       }
    });

   $("#addLocationButton").click(function(){
       $.post("Admin.Locations.AddLocation",$("#adminAddLocationForm").serialize(),function(msg){
           if(msg.length>0){
               if(msg=="true"){
                   setUpLocationTable();
               } else if(msg == "false"){
                   alert("There was an error adding location");
               }
           }
       });
   });

   setUpLocationTable();

   $("#adminLocationPreviousPage").click(function(){
       resetLocationTable();
       page = page-1;
       showLocationPage(page);
   });

   $("#adminLocationNextPage").click(function(){
       resetLocationTable();
       page = page +1;
       showLocationPage(page);
   });


});

function setUpLocationTable(){
    $.post("General.ListLocations","activity=1",function(msg){
        if(msg.length>0){
            activeList = JSON.parse(msg).states;
            resetLocationTable();
            page = 0;
            document.getElementById("adminLocationPreviousPage").disabled = true;
            showLocationPage(0);
        }
    });
}

// Setting up the menu selection to dynamically show only cities for that state
function setUpAdminStates(stateElement, cityElement){
    var stateSelection = document.getElementById(stateElement);
    var citySelection = document.getElementById(cityElement);
    resetParent(stateSelection, "State");
    resetParent(citySelection, "City");
    addChildren(stateSelection,allLocations,0,cityElement);
}
function resetParent(parent, type){
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    var defaultChild = document.createElement("option");
    defaultChild.innerText = "--Select "+type+"--";
    defaultChild.value="default";
    defaultChild.disabled = true;
    defaultChild.selected = true;
    parent.appendChild(defaultChild);
}
function addChildren(parent, array, type, cityElement){
    for(var count=0; count<array.length; count++){
        var child = document.createElement("option");
        child.innerText=array[count].NM;
        child.value = array[count].ID;
        if(type==0){
            child.setAttribute("onclick",
                "setUpAdminCities(\""+cityElement+"\","+array[count].ID+")");
        }
        if(type==1){
            child.setAttribute("onclick",
            "placeLocationMarker("+array[count].LAT+","+array[count].LONG+")");
        }
        parent.appendChild(child);
    }
}
function setUpAdminCities(cityElement,sID){
    var citySelection = document.getElementById(cityElement);
    resetParent(citySelection,"City");
    addChildren(citySelection,allLocations[sID].cities,1);
}

// Setting up the table section for active cities.
function resetLocationTable(){
    var parent = document.getElementById("adminActiveCityList");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}
function generateOntoTable(stateInfo, cityInfo){
    var parent = document.getElementById("adminActiveCityList");
    var row = document.createElement("tr");
    var state = document.createElement("td");
    var city = document.createElement("td");
    var action = document.createElement("td");
    var btn = document.createElement("button");

    state.innerText = stateInfo;
    city.innerText = cityInfo.NM;
    btn.innerText = "Remove";
    btn.setAttribute("onclick","removeLocation("+cityInfo.ID+")");
    action.appendChild(btn);

    row.appendChild(state);
    row.appendChild(city);
    row.appendChild(action);

    parent.appendChild(row);
}

function showLocationPage(page){
    if(page!=0){
        document.getElementById("adminLocationPreviousPage").disabled = false;
    } else {
        document.getElementById("adminLocationPreviousPage").disabled = true;
    }
    var numberPerPage = 15;
    var min = page*numberPerPage;
    var max = (page+1)*numberPerPage;
    var count = 0;
    var displayed = 0;

    console.log(activeList);
    for(var state=0; state<activeList.length; state++){

        console.log(activeList[state]);

        if(activeList[state].cities.length + count > min) {

            for (var city = 0; city < activeList[state].cities.length; city++) {

                if(count<min){
                    count++;
                }else if(count>=min && count<max) {
                    generateOntoTable(activeList[state].NM, activeList[state].cities[city]);
                    count++;
                    displayed++;
                }else{
                    city = activeList[state].cities.length + 1;
                }
            }

            if(count==max){
                state = activeList.length+1;
            }
        } else {
            count += activeList[state].cities.length;
        }
    }

    if(displayed<numberPerPage){
        document.getElementById("adminLocationNextPage").disabled=true;
        while(displayed<numberPerPage){
            blankRows();
            displayed++;
        }
    }else{
        document.getElementById("adminLocationNextPage").disabled=false;
    }

}

function blankRows(){
    var parent = document.getElementById("adminActiveCityList");
    var row = document.createElement("tr");
    var state = document.createElement("td");
    var city = document.createElement("td");
    var action = document.createElement("td");

    state.innerHTML = "&nbsp;";
    city.innerHTML = "&nbsp;";
    action.innerHTML = "&nbsp;";

    row.appendChild(state);
    row.appendChild(city);
    row.appendChild(action);

    parent.appendChild(row);
}
function removeLocation(cityID){
    if(confirm("Are you sure you want to remove this city?")) {
        $.post("Admin.Locations.RemoveLocation", "cityID=" + cityID, function (msg) {
            if (msg.length > 0) {
                if (msg == "true") {
                    setUpLocationTable();
                } else {
                    alert("There was an error");
                }
            }
        });
    }
}