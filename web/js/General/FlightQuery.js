/**
 * Created by johnn on 4/17/2017.
 */
var placeList;
var modelList;
var queryFlights;

var page=0;
var numPerPage = 5;

var map;
var depart;
var arrive;
var path;
var firstSearch = 1;


function myMap() {
    var mapOptions = {
        center: new google.maps.LatLng(44.580207622, -103.461760283),
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);
}

function placeMarker(marker, latt, longg){
    var make = new google.maps.LatLng(latt,longg);
    if(marker==0){
        if(depart!=null){
            depart.setPosition(make);
        }else{
            depart = new google.maps.Marker({
                position: make,
                map:map,
                animation: google.maps.Animation.DROP,
                icon: "/img/takeoff.png"

            });
        }
    }else if(marker==1){
        if(arrive!=null){
            arrive.setPosition(make);
        }else{
            arrive = new google.maps.Marker({
                position: make,
                map:map,
                animation: google.maps.Animation.DROP,
                icon: "/img/landing.png"
            });
        }
    }
    createFlightPath();
}

function createFlightPath(){


    if(depart!=null && arrive!=null && path!=null){
        path.setMap(null);
        path.setPath([depart.getPosition(),arrive.getPosition()]);
        path.setMap(map);
        centerMapOnLocations();
    } else if(depart!=null && arrive!=null){
        path = new google.maps.Polyline({
            path:[depart.getPosition(), arrive.getPosition()],
            geodesic:true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2,
            map: map,
        });
        path.setMap(null);
        path.setPath([depart.getPosition(),arrive.getPosition()]);
        path.setMap(map);
        centerMapOnLocations();
    }else{
        // do nothing - waiting on my input.
    }

}

function centerMapOnLocations(){
    var bounds = new google.maps.LatLngBounds();
    bounds.extend(depart.getPosition());
    bounds.extend(arrive.getPosition());
    var latlng = new google.maps.LatLng((depart.getPosition().lat() + arrive.getPosition().lat())/2,(depart.getPosition().lng() + arrive.getPosition().lng())/2);
    map.panTo(latlng);
    map.fitBounds(bounds);
    map.setZoom(map.getZoom()-1);
}

$(function(){

    $( "#flightQueryDepartDate" ).datepicker({
        beforeShow: function(input,inst){
            var today = new Date();
            today.setDate(today.getDate()+1);
            $(this).datepicker('option','minDate',today);
        }
    });

    $( "#flightQueryReturnDate" ).datepicker({
        beforeShow: function(input,inst){
            var today = new Date();
            today.setDate(today.getDate()+1);
            $(this).datepicker('option','minDate',today);
        }
    });

    $("#flightQueryControls").hide();

    $.post("General.ListLocations","activity=1",function(msg){
        if(msg.length>0){
            placeList = JSON.parse(msg).states;
            setUpStateSelection("flightQueryDepartState","flightQueryDepartCity",0);
            setUpStateSelection("flightQueryArrivalState","flightQueryArrivalCity", 1);
            console.log(placeList);
        }else{
            alert("Could not retrieve information");
        }
    });

    $.post("General.BasicPlaneModels","",function(msg){
        if(msg.length>0){
            modelList = JSON.parse(msg).models;
            //console.log(msg);
            var modelSelect = document.getElementById("flightQueryPlaneModel");
            resetParentSelection(modelSelect,"Model");
            addChildrenToParent(modelSelect,modelList,3,"");
        }
    });

    $("#showGoogleMap").click(function(){
        $("#googlemapContainer").slideToggle("slow");
    })

    $("#flightQueryPrevious").click(function(){
        resetBuildFlightSection();
        page = page-1;
        showFlightQuery(page);
    });

    $("#flightQueryNext").click(function(){
        resetBuildFlightSection();
        page = page +1;
        showFlightQuery(page);
    });

});

function enableReturnDatePicker(){
    document.getElementById("flightQueryReturnDate").disabled = false;
    document.getElementById("flightQueryReturnDate").required = true;
}

function disableReturnDatePicker(){
    document.getElementById("flightQueryReturnDate").disabled = true;
    document.getElementById("flightQueryReturnDate").required = false;
}

function setUpStateSelection(stateElement, cityElement, travelType){
    var stateSelection = document.getElementById(stateElement);
    var citySelection = document.getElementById(cityElement);
    resetParentSelection(stateSelection, "State");
    resetParentSelection(citySelection, "City");
    addChildrenToParent(stateSelection,placeList,0,travelType,cityElement);
}
// this is made during the option tags creation process.
function setUpCitySelection(cityElement, sID, travelType){
    var citySelection = document.getElementById(cityElement);
    resetParentSelection(citySelection,"City");
    addChildrenToParent(citySelection,placeList[sID].cities,1, travelType, cityElement);
}
function resetParentSelection(parent, type){
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
function addChildrenToParent(parent, array, type, travelType, posChild){
    for(var count=0; count<array.length; count++){
        var child = document.createElement("option");
        child.innerText=array[count].NM;
        child.value = array[count].ID;
        if(type==0){
            child.setAttribute("onclick",
                "setUpCitySelection(\""+posChild+"\","+array[count].ID+","+travelType+")");
        }
        if(type==1){
            child.setAttribute("onclick","placeMarker(\""+travelType+"\","+array[count].LAT+","+array[count].LONG+")");
        }
        parent.appendChild(child);
    }
}

function attemptFlightQuery(){
    var criteria = $("#flightQueryForm").serialize();

    console.log(criteria);

    $.post("General.FlightQuery", criteria, function(msg){
        if(msg.length>0){
            queryFlights = JSON.parse(msg).trips;
            console.log(msg);
            if(queryFlights.length>0) {
                $("#flightQueryControls").show();
                resetBuildFlightSection();
                page = 0;
                showFlightQuery(page);
                if(firstSearch==1){
                    $("#googlemapContainer").slideUp("1000");
                    firstSearch = 0;
                }
            }else{
                alert("No flights are currently set for this criteria");
            }

        }
    });

    return false;
}


function showFlightQuery(page){
    if(page!=0){
        document.getElementById("flightQueryPrevious").disabled = false;
    } else {
        document.getElementById("flightQueryPrevious").disabled = true;
    }
    var min = page*numPerPage;
    var max = (page+1)*numPerPage;
    var count = 0;
    var displayed = 0;

    for(var fly=0; fly<queryFlights.length; fly++){
        console.log("flight#"+fly);
        if(count<min){
            count++;
        }else if(count>=min && count<max){
            buildFlightInfo(queryFlights[fly].flights);
            displayed++;
            count++;
        }

        if(count==max){
            fly = queryFlights.length + 1;
        }
    }

    if(displayed<numPerPage){
        document.getElementById("flightQueryNext").disabled=true;
        /*while(displayed<numberPerPage){
            displayed++;
        }*/
    }else{
        document.getElementById("flightQueryNext").disabled=false;
    }

}

function resetBuildFlightSection(){
    var parent = document.getElementById("flightQueryView");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.lastChild);
    }
}


function buildFlightInfo(trip){
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};

    if(trip[0].dLoc == $("#flightQueryArrivalCity").val()){
        return;
    }
    var parent = document.getElementById("flightQueryView");
    var rTrip = 3;

    console.log(trip);

    if(trip.length<4 && trip.length>1){
        for(var setColors=1; setColors<trip.length; setColors++){
            if(trip[setColors].dLoc == $("#flightQueryArrivalCity").val()){
                rTrip=setColors;
            }
        }
    }


    for(var flights = 0; flights<trip.length; flights++){
        var container = document.createElement("div");
        var leftSide = document.createElement("div");
        leftSide.appendChild(buildFlightLeftSide(trip,flights));
        container.appendChild(leftSide);
        if(flights==0) {
            var rightSide = document.createElement("div");
            rightSide.appendChild(buildFlightRightSide(trip, flights));
            container.appendChild(rightSide);
        }

        if(flights!=0 && flights<rTrip){
            container.setAttribute("class","one-way");
        }
        if(flights!=0 && flights>rTrip-1){
            container.setAttribute("class","return-trip");
        }
        parent.appendChild(container);
    }

}

function buildFlightLeftSide(flight, number){
    var unorderedList = document.createElement("ul");
    var dates = document.createElement("li");
    var times = document.createElement("li");
    var locations = document.createElement("li");
    var planeInfo = document.createElement("li");
    var timeText = document.createElement("p");

    timeText.innerHTML = timeFormatter(flight[number].dTime) + "&nbsp;&nbsp;&rightarrow;&nbsp;&nbsp;" +timeFormatter(flight[number].aTime);
    timeText.setAttribute("class","flightQueryTime");
    dates.innerHTML = dateFormatter(flight[number].dDate, flight[number].aDate);
    locations.innerHTML = locationFormatter(flight[number].dLoc,flight[number].aLoc);
    planeInfo.innerText = modelList[modelFinder(flight[number].mID)].NM + " : IA" +flight[number].pID;

    console.log("finished model finder");
    times.appendChild(timeText);
    unorderedList.append(times);
    unorderedList.appendChild(document.createElement("br"));
    unorderedList.append(dates);
    unorderedList.append(locations);
    unorderedList.append(planeInfo);
    return unorderedList;
}

function buildFlightRightSide(trip, number){
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};
    //String[] mList = {"ID","NM","BP"};
    var firstWrap = document.createElement("div");
    var busWrap = document.createElement("div");
    var ecoWrap = document.createElement("div");
    var sideBySide = document.createElement("div");
    var first = document.createElement("button");
    var business = document.createElement("button");
    var economy = document.createElement("button");

    console.log("calculating prices...");

    if(number==0){

        var totalFirst = 0;
        var totalBus = 0;
        var totalEcon = 0;
        var allFlightID = ["null","null","null","null","null","null"];
        for(var flights=0; flights<trip.length; flights++) {
            var basePrice = parseInt(modelList[modelFinder(trip[flights].mID)].BP);
            var adjustPrice = parseInt(trip[flights].DP) * parseInt(trip[flights].Dem);
            totalFirst += totalFirst += Math.floor(basePrice + adjustPrice * (2));
            totalBus += Math.floor(basePrice + adjustPrice * (1.5));
            totalEcon += Math.floor(basePrice + adjustPrice * (1));
            allFlightID[flights] = trip[flights].fID;
        }

        first.innerText="$ "+ totalFirst; // 2 for first class
        business.innerText="$ "+ totalBus; // 1.5 for first class
        economy.innerText="$ "+ totalEcon ; // 1 for first class

        first.setAttribute("class","buyFirstClass");
        business.setAttribute("class","buyBusinessClass");
        economy.setAttribute("class","buyEconomyClass");

        first.setAttribute("onclick","sendToBooking("+allFlightID[0]+","+allFlightID[1]+","+allFlightID[2]+","+allFlightID[3]+","+allFlightID[4]+","+allFlightID[5]+",2)");
        business.setAttribute("onclick","sendToBooking("+allFlightID[0]+","+allFlightID[1]+","+allFlightID[2]+","+allFlightID[3]+","+allFlightID[4]+","+allFlightID[5]+",1)");
        economy.setAttribute("onclick","sendToBooking("+allFlightID[0]+","+allFlightID[1]+","+allFlightID[2]+","+allFlightID[3]+","+allFlightID[4]+","+allFlightID[5]+",0)");

        if(trip[number].aEcon=="0"){
            economy.disabled=true;
            economy.innerText="";
        }
        if(trip[number].aBus=="0"){
            business.disabled=true;
            business.innerText="";
        }
        if(trip[number].aFirst=="0"){
            first.disabled=true;
            first.innerText="";
        }

        firstWrap.appendChild(first);
        busWrap.appendChild(business);
        ecoWrap.appendChild(economy);

        sideBySide.appendChild(ecoWrap);
        sideBySide.appendChild(busWrap);
        sideBySide.appendChild(firstWrap);
    }

    return sideBySide;
}

function timeFormatter(time){

    var array = time.split(":");
    var hours = parseInt(array[0]);
    var mins = parseInt(array[1]);
    var half = "UTC";

    if(hours<12){
        if(hours==0){
            hours=12;
        }
        if(hours<10){
            hours = "0"+String(hours);
        }
        half = "am";
    }else{
        if(hours!=12) {
            hours = hours - 12;
        }
        half = "pm";
    }

    if(mins<10){
        mins = "0"+String(mins);
    }

    return (String(hours) + ":" + String(mins) + " " + half);
}

function dateFormatter(date1, date2){

    var monthNM = ["January","February","March","April","May","June","July","August","September","October","November","December"];
    var dayNM = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];

    var array1 = date1.split("-");
    var array2 = date2.split("-");

    var formatDate1 = new Date(parseInt(array1[0]),parseInt(array1[1])-1,parseInt(array1[2]));
    var formatDate2 = new Date(parseInt(array2[0]),parseInt(array2[1])-1,parseInt(array2[2]));

    //var year =[formatDate1.getYear(),formatDate2.getYear()];
    var month =[monthNM[formatDate1.getMonth()],monthNM[formatDate2.getMonth()]];
    var day =[dayNM[formatDate1.getDay()],dayNM[formatDate2.getDay()]];

    var totalDate = day[0]+", "+month[0]+" "+array1[2];

    if(date1!=date2){
        totalDate += "&nbsp;&rightarrow;&nbsp;" + day[1]+", "+month[1]+" "+array2[2]
    }
    return totalDate;
}

function modelFinder(modelID){
    var modelListID="";
    for(var i=0; i<modelList.length; i++){
        if(modelList[i].ID == modelID){
            modelListID = i;
            i = modelList.length+1;
        }
    }
    return modelListID;
}

function locationFormatter(place1, place2){
    var place1Bool=false;
    var place2Bool=false;
    var place1Text="";
    var place2Text="";
    for(var states=0; states<placeList.length; states++){
        for(var cities=0; cities<placeList[states].cities.length; cities++){
            if(parseInt(place1) == placeList[states].cities[cities].ID){
                place1Text += placeList[states].cities[cities].NM + ", " + placeList[states].NM;
                place1Bool = true;
            }
            if(parseInt(place2) ==placeList[states].cities[cities].ID){
                place2Text += placeList[states].cities[cities].NM + ", "+placeList[states].NM;
                place2Bool = true;
            }
            if(place1Bool && place2Bool){
                cities = placeList[states].cities.length+1;
            }
        }
        if(place1Bool && place2Bool){
            states = placeList[states].length+1;
        }
    }

    return place1Text+"&nbsp;&nbsp;&nbsp;&rightarrow;&nbsp;&nbsp;&nbsp;"+place2Text;

}

function sendToBooking(flight1,flight2,flight3,flight4,flight5,flight6,classID){
    var tickets = $("#flightQueryNumberOfPassengers").val();
    if(tickets==null){
        tickets = 1;
    }
    var info = "flight1="+flight1+"&flight2="+flight2+"&flight3="+flight3+"&flight4="+flight4+"&flight5="+flight5+"&flight6="+flight6+"&classID="+classID+"&tickets="+tickets;
    window.location = "BookingFlight.jsp?"+info;
}