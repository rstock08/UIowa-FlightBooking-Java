/**
 * Created by johnn on 4/16/2017.
 */
/**
 * Created by johnn on 4/11/2017.
 */
var planeModelList ="";
var availablePlaneList = "";
var flightModalPage = 0;
var flightList;
var flightPage = 0;
var flightsPerPage = 20;

/**
 * Date picker for Flight Departure
 */
$( function() {

    console.log("Starting Flights Page");
    createPlaneModelSelection();
    updateFlightTable();
    console.log("Starting Flights Page");

    $( "#flightDeparturedatepicker" ).datepicker({
        beforeShow: function(input,inst){
            var today = new Date();
            today.setDate(today.getDate()+1);
            $(this).datepicker('option','minDate',today);
        }
    });

    $( "#flightArrivaldatepicker" ).datepicker({
        beforeShow: function(input,inst){
            var today = new Date();
            today.setDate(today.getDate()+1);
            $(this).datepicker('option','minDate',today);
        }
    });

    $( "#flightDeparturetimepicker" ).timepicker({
        timeFormat: 'HH:mm',
        minTime: new Date(0,0,0,0,0)
    });

    $( "#flightArrivaltimepicker" ).timepicker({
        timeFormat: 'HH:mm',
        minTime: new Date(0,0,0,0,0)
    });

    $(".adminAddFlightModalButtons").onclick=function(){
        var prev = document.getElementById("adminAddFlightModalPreviousPage");
        var next = document.getElementById("adminAddFlightModalCurrentPage");
        if(this == prev){
            getAvailableAirplanes(prev);
        }else if(this == next){
            getAvailableAirplanes(next);
        }
    };

    $("#adminFlightListTablePreviousPage").click(function(){
        removeAdminFlightList();
        flightPage = flightPage-1;
        showAdminFlightsPage(flightPage);
    });

    $("#adminFlightListTableNextPage").click(function(){
        removeAdminFlightList();
        flightPage = flightPage +1;
        showAdminFlightsPage(flightPage);
    });


} );


function enableNextEntry(number){
    if(number == 1){
        document.getElementById("flightDeparturetimepicker").disabled = false;
    } else if (number == 2){
        document.getElementById("flightDepartureLocationState").disabled = false;
    } else if (number == 3){
        document.getElementById("flightDepartureLocationCity").disabled = false;
    } else if (number == 4){
        document.getElementById("flightArrivalLocationState").disabled = false;
    } else if (number == 5){
        document.getElementById("flightArrivalLocationCity").disabled = false;
    } else if (number == 6){
        document.getElementById("flightPlaneModelSelect").disabled = false;
    } else if (number == 7){
        document.getElementById("addFlightButton").disabled = false;
    } else {
        document.getElementById("flightDeparturetimepicker").disabled = true;
        document.getElementById("flightDepartureLocationState").disabled = true;
        document.getElementById("flightDepartureLocationCity").disabled = true;
        document.getElementById("flightArrivalLocationState").disabled = true;
        document.getElementById("flightArrivalLocationCity").disabled = true;
        document.getElementById("flightPlaneModelSelect").disabled = true;
        document.getElementById("addFlightButton").disabled = true;
    }
}


/**
 * Implemented to only show cities that go with the Departure State
 * @param classID or the class state ID
 */
function alterDepartureCitySelect(classID){
    var allCities = document.getElementById("flightDepartureLocationCity").getElementsByClassName("adminAllCities");

    for(var city=0; city<allCities.length; city++){
        allCities[city].style.display = "none";
    }

    var stateCities = document.getElementById("flightDepartureLocationCity").getElementsByClassName(classID);

    for(var stateCity=0; stateCity<stateCities.length; stateCity++){
        stateCities[stateCity].style.display = "block";
    }

}

/**
 * Implemented to only show cities that go with the Arrival State
 * @param classID or state ID class name
 */
function alterArrivalCitySelect(classID){

    var departStateSelect = document.getElementById("flightDepartureLocationState");
    var departState = departStateSelect.options[departStateSelect.selectedIndex].value;
    var departCitySelect = document.getElementById("flightDepartureLocationCity");
    var departCity = departCitySelect.options[departCitySelect.selectedIndex].value;
    var arrivalStateSelect = document.getElementById("flightArrivalLocationState");
    var arrivalState = arrivalStateSelect.options[arrivalStateSelect.selectedIndex].value;
    var allCities = document.getElementById("flightArrivalLocationCity").getElementsByClassName("adminAllCities");

    for(var city=0; city<allCities.length; city++){
        allCities[city].style.display = "none";
    }

    var stateCities = document.getElementById("flightArrivalLocationCity").getElementsByClassName(classID);

    for(var stateCity=0; stateCity<stateCities.length; stateCity++){
        stateCities[stateCity].style.display = "block";
        if(departState==arrivalState && departCity==stateCities[stateCity].value){
            stateCities[stateCity].style.display = "none";
        }
    }

}

/**
 * Gets the departure and arrival location information
 * @returns {*|jQuery} serialized information for jquery send
 */
function getLocationInfo(){
    var locations;
    locations =  $("#flightDepartureLocationState").serialize()+"&";
    locations +=  $("#flightDepartureLocationCity").serialize()+"&";
    locations +=  $("#flightArrivalLocationState").serialize()+"&";
    locations +=  $("#flightArrivalLocationCity").serialize();
    return locations;
}


function createPlaneModelSelection(){
    console.log("building plane models table");

    $.get("Admin.Planes.PlaneModelsList","",function(msg){
        console.log("plane msg = "+msg);
        if(msg.length>0){
            planeModelList = JSON.parse(msg).models;
            console.log("finished parsing planes msg");
            resetDisplayPlaneModelSelection();
            for (var i = 0; i < planeModelList.length; i++) {
                console.log("at plane #"+i);
                displayPlaneModel(i);
            }
        }
    });
}

function displayPlaneModel(count){
    var parent = document.getElementById("flightPlaneModelSelect");
    var child = document.createElement("option");
    child.value = planeModelList[count].mID;
    child.innerText = planeModelList[count].mModel;
    parent.appendChild(child);
}

function resetDisplayPlaneModelSelection(){
    var parent = document.getElementById("flightPlaneModelSelect");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    var child = document.createElement("option");
    child.innerText = "---Select Model---";
    child.selected = true;
    child.disabled = true;
    parent.appendChild(child);
}

/**
 * Determines if the selected plane model can travel the distance
 * "doGets" the information to "IowaAir.Admin.AdminFlights"
 * The returned information includes a simple boolean, trip time,
 * and price adjustment due to distance traveled
 */
function canModelMakeTheDistance(){
    console.log("Determining if this model is valid...");
    document.getElementById("flightDemandSlider").value = 1; // resets slider
    updateSliderText(); // updates slider text to new value
    availablePlaneList = "";
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var planeModelID = planeModelSelect.options[planeModelSelect.selectedIndex].value;
    var series = getLocationInfo() + "&flightPlaneModelSelect="+planeModelID; // now it is just a number, not text. yay.

    $.get(
        "Admin.Flights.CheckModelChoiceIsValid", series,
        function(msg){
            console.log(msg);
            var ableTimedPrice = JSON.parse(msg);
            if(ableTimedPrice.canTravel == 1){
                modifyArrivalDateAndTime(ableTimedPrice.timed);
                document.getElementById("flightDistancePrice").value = ableTimedPrice.distancePrice;
                document.getElementById("adminAddFlightModalCurrentPage").innerHTML = "0";
                getAvailableAirplanes(0);
            } else{
                alert("this plane can't make the trip");
            }
        }
    );

}

/**
 * Sets the Arrival Date and Time once locations known.
 * @param tripTime
 */
function modifyArrivalDateAndTime(tripTime){
    var departDate = $("#flightDeparturedatepicker").datepicker('getDate');
    var departTime = $("#flightDeparturetimepicker").timepicker('getTime');
    var arrivalDate = new Date();

    console.log(tripTime);

    arrivalDate.setFullYear(departDate.getFullYear());
    arrivalDate.setMonth(departDate.getMonth());
    arrivalDate.setDate(departDate.getDate());
    arrivalDate.setHours(departTime.getHours());
    arrivalDate.setMinutes(departTime.getMinutes()+tripTime);

    $("#flightArrivaldatepicker").datepicker('setDate',arrivalDate);
    $("#flightArrivaltimepicker").timepicker('setTime',arrivalDate);
}


function getAvailableAirplanes(page){
    console.log("getting available planes...");
    //String[] list = {"pID","mID"};
    $.post("Admin.Flights.GetAvailablePlanes",getAvailabilityInfo(0),
        function(msg){
            var response = JSON.parse(msg);
            removePreviousPlaneQuery();
            availablePlaneList = response.planes;
            console.log(response.numberOfPlanes);
            if(!response.numberOfPlanes == 0){
                setModalHeader();
                document.getElementById("adminAddFlightModalFormNumber").value = availablePlaneList.length;
                console.log(availablePlaneList.length);
                flightModalPage = 0;
                whatAirplanesToDisplay(flightModalPage);
            }else{
                // Code for when the number of planes that match the criteria is 0.
            }
            resetAddFlightButtonValue();
        }
    );

}

function whatAirplanesToDisplay(page){

    for(var i=page*10; i<page*10+10; i++){
        if(i < availablePlaneList.length) {
            displayAvailablePlane(availablePlaneList[i].pID);
        }
    }
    hideShowButtons(page);
}

/**
 * Gets the information to send to get list of available airplanes from server
 * @returns {string|*} of serialized information to pass to server
 */
function getAvailabilityInfo(adjust){
    var departDate = $("#flightDeparturedatepicker").datepicker('getDate');
    var departTime = $("#flightDeparturetimepicker").timepicker('getTime');
    var arrivalDate = $("#flightArrivaldatepicker").datepicker('getDate');
    var arrivalTime = $("#flightArrivaltimepicker").timepicker('getTime');
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var nextSeries;

    var newDepart = new Date(departDate.getFullYear(), departDate.getMonth(), departDate.getDate()+adjust)
    var newArrive = new Date(departDate.getFullYear(), departDate.getMonth(), departDate.getDate()+adjust)

    nextSeries =    "flightDepartureDate="+returnDateInMSQLFormat(newDepart,1) + "&";
    nextSeries +=   "flightDepartureTime="+returnTimeInMSQLFormat(departTime)+":00&";
    nextSeries +=   "flightArrivalDate="+returnDateInMSQLFormat(newArrive,1)+"&";
    nextSeries +=   "flightArrivalTime="+returnTimeInMSQLFormat(arrivalTime)+":00&";
    nextSeries +=   "flightPlaneModelSelect="+planeModelSelect.options[planeModelSelect.selectedIndex].value;
    return nextSeries;
}

function returnDateInMSQLFormat(date, adjust){
    var adjustMonth = date.getMonth() + adjust; // 0 if going to be parsed. 1 if going to be stored in database
    return date.getFullYear() + "-" + ((adjustMonth< 10) ? "0" : "") + adjustMonth + "-" + ((date.getDate() < 10) ? "0" : "") + date.getDate();
}

function returnTimeInMSQLFormat(time){
    return ((time.getHours() < 10) ? "0" : "") + time.getHours() + ":" + ((time.getMinutes() < 10) ? "0" : "") + time.getMinutes();
}

//String[] list = {"planeType","id","capacity","maxEcon","maxBus","maxFirst","basePrice"};
function displayAvailablePlane(pID){
    var ul = document.getElementById("adminAddFlightModalUL");
    var li = document.createElement("li");
    var liDivPlane = document.createElement("div");
    var liDivCenter = document.createElement("div");
    var liDivResponse = document.createElement("div");
    var liDivP = document.createElement("p");
    var liDivInput = document.createElement("input");

    liDivInput.type="checkbox";
    liDivInput.class="addFlightCheckBoxes";
    liDivInput.value=pID;
    liDivInput.onclick = function(){
        console.log(this.value);
        var addFlightButtonInstant = document.getElementById("adminAddFlightModalFormButton");
        addFlightButtonInstant.value = this.value;
        addFlightButtonInstant.disabled = false;

    };

    liDivP.innerHTML="ID: "+pID;
    liDivCenter.innerHTML="";
    liDivPlane.appendChild(liDivP);
    liDivResponse.appendChild(liDivInput);

    li.appendChild(liDivPlane);
    li.appendChild(liDivCenter);
    li.appendChild(document.createElement("div"));
    li.appendChild(liDivResponse);
    li.name = "flightAddFlightFormOption";

    ul.appendChild(li);
}

/**
 * Opens up the modal that shows all available airplanes
 */
function viewAddFlightModal() {
    document.getElementById("adminAddFlightModal").style.display = "block";
    return false;
}

/**
 * Closes the modal that shows all available airplanes
 */
function closeAddFlightModal(){
    document.getElementById("adminAddFlightModal").style.display = "none";
}

/**
 * Removes the previous searches available airplane list
 */
function removePreviousPlaneQuery(){
    var parent = document.getElementById("adminAddFlightModalUL");
    while(parent.hasChildNodes()) {
        parent.removeChild(parent.firstChild);
    }
}

function hideShowButtons(page){
    var current = document.getElementById("adminAddFlightModalCurrentPage");
    var prev = document.getElementById("adminAddFlightModalPreviousPage");
    var next = document.getElementById("adminAddFlightModalNextPage");
    var prevPage = page-1;
    current.innerHTML=page;
    if(prevPage<1){
        prev.hidden = true;
    }else{
        prev.show = true;
    }
    if(page === 0){
        current.hidden = true;
    } else {
        current.show = true;
    }
    if((page+1)*10<availablePlaneList.length){
        next.show = true;
    } else {
        next.hidden = true;
    }
}

function setModalHeader(){
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var departStateSelect = document.getElementById("flightDepartureLocationState");
    var departState = departStateSelect.options[departStateSelect.selectedIndex].value;
    var departCitySelect = document.getElementById("flightDepartureLocationCity");
    var departCity = departCitySelect.options[departCitySelect.selectedIndex].value;
    var arrivalStateSelect = document.getElementById("flightArrivalLocationState");
    var arrivalState = arrivalStateSelect.options[arrivalStateSelect.selectedIndex].value;
    var arrivalCitySelect = document.getElementById("flightArrivalLocationCity");
    var arrivalCity = arrivalCitySelect.options[arrivalCitySelect.selectedIndex].value;

    document.getElementById("adminFlightModalDepartDate").innerText = returnDateInMSQLFormat($("#flightDeparturedatepicker").datepicker('getDate'), 1);
    document.getElementById("adminFlightModalDepartTime").innerText = returnTimeInMSQLFormat($("#flightDeparturetimepicker").timepicker('getTime'));
    document.getElementById("adminFlightModalArriveDate").innerText = returnDateInMSQLFormat($("#flightArrivaldatepicker").datepicker('getDate'), 1);
    document.getElementById("adminFlightModalArriveTime").innerText = returnTimeInMSQLFormat($("#flightArrivaltimepicker").timepicker('getTime'));
    document.getElementById("adminFlightModalDepartLocation").innerText = departCity + ", " + departState;
    document.getElementById("adminFlightModalArriveLocation").innerText = arrivalCity + ", " + arrivalState;
    document.getElementById("adminAddFlightModalTitle").innerText = planeModelSelect.options[planeModelSelect.selectedIndex].text;
}

function submitToDatabase(){
    if(confirm("Are you sure?")){
        console.log("sending flight to database...");

        var occurrence = $("#flightOccurrenceSelect").serialize();
        var timePeriod = $("#flightOccurrencePeriodSelect").serialize();

        var data = getAvailabilityInfo(0) + "&" + getLocationInfo() + "&value=";
        data += document.getElementById("adminAddFlightModalFormButton").value;
        data += "&distancePrice="+document.getElementById("flightDistancePrice").value;
        data += "&flightDemandSlider="+document.getElementById("flightDemandSlider").value;
        data += "&"+occurrence+"&"+timePeriod;

        console.log(data);
        attemptAddToDatabase(data);
    } else {

    }
}

function attemptAddToDatabase(data){
    $.get("Admin.Flights.AddFlight", data,
        function (msg) {
            closeAddFlightModal();
            resetAllFlightInformationInputs();
            removeAdminFlightList();
            updateFlightTable();
        }
    );
}

function infoToSubmitToDatabase(adjust){

}

function updateSliderText(){
    document.getElementById("flightDemandValue").value = document.getElementById("flightDemandSlider").value;
}

function resetAddFlightButtonValue(){
    var addFlightButton = document.getElementById("adminAddFlightModalFormButton");
    addFlightButton.value="";
    addFlightButton.disabled = true;
}

function resetAllFlightInformationInputs(){
    document.getElementById("adminFlightsForm").reset();
}


// Flight Table Functions
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function updateFlightTable(){
    $.post("Admin.Flights.GetFlightsList","",function(msg){

        if(msg.length>0){
            flightList = JSON.parse(msg).flightList;
            flightPage = 0;
            showAdminFlightsPage(flightPage);
        }

    });

}

function showAdminFlightsPage(page){

    if(page!=0){
        document.getElementById("adminFlightListTablePreviousPage").disabled = false;
    } else {
        document.getElementById("adminFlightListTablePreviousPage").disabled = true;
    }
    var min = page*flightsPerPage;
    var max = (page+1)*flightsPerPage;
    var count = 0;
    var displayed = 0;

    for(var flight=0; flight<flightList.length; flight++){
        if(count<min){
            count++;
        }else if(count>=min && count<max){
            displayFlightRow(flightList[flight]);
            displayed++;
            count++;
        }

        if(count==max){
            flight = flightList.length + 1;
        }
    }

    if(displayed<flightsPerPage){
        document.getElementById("adminFlightListTableNextPage").disabled=true;
    }else{
        document.getElementById("adminFlightListTableNextPage").disabled=false;
    }

}

function displayFlightRow(flight){

    var list = ["fID","pID","dDate","dTime","dCity","dState","aDate","aTime","aCity","aState","aEcon","aBus","aFirst"];
    var parent = document.getElementById("adminFlightListTable");
    var child = document.createElement("tr");

    for(var i=0; i<list.length; i++){
        var subChild = document.createElement("td");
        subChild.innerText = flight[list[i]]; // for all other table entities
        subChild.name="col"+i;
        child.appendChild(subChild); // adds table data to table row
    }

    parent.appendChild(child); // adds table row to table

}

function removeAdminFlightList(){
    var parent = document.getElementById("adminFlightListTable");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}
























