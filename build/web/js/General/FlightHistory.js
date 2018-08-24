/**
 * Created by johnn on 4/28/2017.
 */

var activeList;

$(function() {
    $.post("/Customer.FlightHistory.History", "", function (msg) {
        if (msg.length > 0) {
            activeList = JSON.parse(msg).history;
            console.log(activeList);
            setUpFlightsTable();
        }
    });

});



function setUpFlightsTable(){
        resetFlightsTable();
        showFlightsPage();
}
function resetFlightsTable(){
    var parent = document.getElementById("flightHistoryList");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}

function generateOntoTable(id,totalTix,economyTix,businessTix,firstTix,depDate,depTime,depLoc,ariDate,ariTime,ariLoc){
    var parent = document.getElementById("flightHistoryList");
    var row = document.createElement("tr");
    var flightId = document.createElement("td");
    var totalTickets = document.createElement("td");
    var economy = document.createElement("td");
    var business = document.createElement("td");
    var first = document.createElement("td");
    var departureDate = document.createElement("td");
    var departureTime = document.createElement("td");
    var departureLocation = document.createElement("td");
    var arrivalDate = document.createElement("td");
    var arrivalTime = document.createElement("td");
    var arrivalLocation = document.createElement("td");


    flightId.innerText = id;
    totalTickets.innerText = totalTix;
    economy.innerText = economyTix;
    business.innerText = businessTix;
    first.innerText = firstTix;
    departureDate.innerText = depDate;
    departureTime.innerText = depTime;
    departureLocation.innerText = depLoc;
    arrivalDate.innerText = ariDate;
    arrivalTime.innerText = ariTime;
    arrivalLocation.innerText = ariLoc;



    row.appendChild(flightId);
    row.appendChild(totalTickets);
    row.appendChild(economy);
    row.appendChild(business);
    row.appendChild(first);
    row.appendChild(departureDate);
    row.appendChild(departureTime);
    row.appendChild(departureLocation);
    row.appendChild(arrivalDate);
    row.appendChild(arrivalTime);
    row.appendChild(arrivalLocation);
    parent.appendChild(row);
}

function showFlightsPage(){
    console.log(activeList);
    for(var i=0; i<activeList.length; i++){
        console.log(activeList[i]);
        generateOntoTable(activeList[i]['flightID'], activeList[i]['totalTickets'], activeList[i]['ticketsEconomy'], activeList[i]['ticketsBusiness'], activeList[i]['ticketsFirst'], activeList[i]['departureDate']
            , activeList[i]['departureTime'], activeList[i]['departureLocation'], activeList[i]['arrivalDate'], activeList[i]['arrivalTime'], activeList[i]['arrivalLocation']);

    }
}
/*




$.post("path name", info to send... your case is "" , function(response){

});




 */