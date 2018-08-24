/**
 * Created by johnn on 4/28/2017.
 */

var flightInfo;
var modelList;
var totalCost=0;

$(function(){

    $.post("General.BasicPlaneModels","",function(msg){
        if(msg.length>0){
            modelList = JSON.parse(msg).models;
            getFlightInformation();
        }
    });

    getFlightInformation();
    setBookingFormInputs();

});

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function getFlightInformation(){

    var flight1 = getUrlParameter("flight1");
    var flight2 = getUrlParameter("flight2");
    var flight3 = getUrlParameter("flight3");
    var flight4 = getUrlParameter("flight4");
    var flight5 = getUrlParameter("flight5");
    var flight6 = getUrlParameter("flight6");

    var data = "flight1="+flight1+"&flight2="+flight2+"&flight3="+flight3+"&flight4="+flight4+"&flight5="+flight5+"&flight6="+flight6;

    $.get("General.FlightQuery",data, function(msg){
        console.log(msg);
        resetBookingTable();
        if(msg.length>0){

            flightInfo = JSON.parse(msg).flights;
            createBookingTable();
        }

    });

}


function createBookingTable(){
    var parent = document.getElementById("bookingTable");
    var tickets  =getUrlParameter("tickets");
    var classID  =getUrlParameter("classID");
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};

    var list=["fID","dDate","dTime","aDate","aTime","pID","mID"];

    for(var count=0; count<flightInfo.length; count++){
        var row = document.createElement("tr");
        for(var inner=0;inner<list.length;inner++){
            var column = document.createElement("td");

            if(inner==list.length-2){
                column.innerText = modelList[modelFinder(flightInfo[count].mID)].NM;
            }else if (inner==list.length-1) {
                column.innerText = "IA"+flightInfo[count][list[inner]];
            }else{
                column.innerText = flightInfo[count][list[inner]];
            }

            row.appendChild(column);

        }
        var ticketCol = document.createElement("td");
        var classCol = document.createElement("td");
        ticketCol.innerText = tickets;
        classCol.innerText = (classID==0)? "Economy" : ((classID==1)?"Business": ((classID==2)?"First":"") );

        row.appendChild(ticketCol);
        row.appendChild(classCol);
        parent.appendChild(row);

        addToTotal(flightInfo[count],tickets,classID);
    }

    document.getElementById("totalCostDisplay").innerText = "Total:  $"+totalCost.toString()+".00";
    document.getElementById("totalCost").value = totalCost;
}

function resetBookingTable(){
    var parent = document.getElementById("bookingTable");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    totalCost=0;
    document.getElementById("totalCost").value = totalCost;
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

function addToTotal(flight,tickets,classID){

    console.log(flight.mID);
    console.log(modelFinder(flight.mID));
    console.log(modelList[modelFinder(flight.mID)]);
    var basePrice = parseInt(modelList[modelFinder(flight.mID)].BP);
    var adjustPrice = parseInt(flight.DP)*parseInt(flight.Dem);

    var classPriceAdjust = (classID==0)?1:(classID==2)?1.5:(classID)?2:-1;

    if(classPriceAdjust!=-1){
        totalCost += tickets * Math.floor(basePrice + adjustPrice*classPriceAdjust);
    }

}

function getPaymentInfo(){

    /*

        You will need to get the session variable "userEmail" on the servlet side.
        I imagine its as easy as session.getAttribute("userEmail") or something.
        make the JSON response be in the following format
        I already made the servlet - just fill in the code. check $.post for path.

        {
            cards:[
                    {ID:"index value",NM:"whatever",L4:"last 4 digits"} , ....more cards
                  ]
        }

        $.post("Customer.PaymentInfo.GetPaymentTypes", "", function(msg){

            if(msg.length>0){

                var paymentInfo = JSON.parse(msg).cards;

                resetCardSelection();
                for(var card=0; card<paymentInfo.length; card++){

                    displayCard(paymentInfo[card]);

                }
            }
        });





     */
}

function displayCard(paymentInfo){
    var parent = document.getElementById("bookingPaymentInfo");
    var card = document.createElement("option");
    card.innerText = paymentInfo.NM + " - "+paymentInfo.L4;
    card.value = paymentInfo.ID;
    parent.appendChild(card);
}

function resetCardSelection(){
    var parent = document.getElementById("bookingPaymentInfo");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    var defChild = document.createElement("option");
    defChild.innerText = "--Select Payment--";
    defChild.disabled = true;
    defChild.selected = true;
    parent.appendChild(defChild);
}

function checkCVC(){

    var data = $("#bookingForm").serialize();

    /*

    I already made the servlet - just fill in the code. check $.post for path.

    Check the names on the bookingForm to see the getParameter("dsfs") calls are.
    I included two obvious names in case i can get the onestop and twostop flight functionality.
    if those fields are not null, then perform the process on them with the tickets/classID.

    for this servlet, have it grab the session email again to get the user ID
    check the users ID with the card ID and verify the CVC code is correct.

    If it is, process the purchase immediately on the servlet side since all the info is there and return "true"
        Processing does include:
            - sending an email to the user about their purchase!
                - Send email at end so you can compile everything. Dont send email for each stop along trip -.-
            - updating the flights available/taken seats
                - use the classPurchased field to figure out which one to alter. Eco = 0 , Bus = 1, First = 2
            - Anything else i'm forgetting?...

    If it is not, do not process and return "false".



    $.post("Customer.PaymentInfo.ProcessPayment", data, function(msg){

        if(msg.length>0){
            if(msg=="true"){
                confirm("Thank you for booking with us! Your receipt has been emailed to you!");
                window.location = "index.jsp";
            }else{
                alert("Incorrect CVC");
            }
        }

    ));

    */

    return false; // this is just so the form doesn't submit since we are doing everything with the JQUERY Post
}

function setBookingFormInputs(){
    document.getElementById("flight1").value = getUrlParameter("flight1");
    document.getElementById("flight2").value = getUrlParameter("flight2");
    document.getElementById("flight3").value = getUrlParameter("flight3");
    document.getElementById("flight4").value = getUrlParameter("flight4");
    document.getElementById("flight5").value = getUrlParameter("flight5");
    document.getElementById("flight6").value = getUrlParameter("flight6");
    document.getElementById("classPurchased").value = getUrlParameter("classID");
    document.getElementById("ticketsPurchased").value = getUrlParameter("tickets");
}