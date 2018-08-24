/**
 * Created by Charlie on 5/1/2017.
 */
var activeList;

$(function() {
    $.post("/Customer.PaymentInfo.PaymentTableServlet", "", function (msg) {
        if (msg.length > 0) {
            activeList = JSON.parse(msg).payment;
            console.log(activeList);
            setUpPaymentTable();
        }
    });

});



function setUpPaymentTable(){
    resetPaymentTable();
    showPaymentPage();
}
function resetPaymentTable(){
    var parent = document.getElementById("billingList");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}

function showPaymentPage(){
    console.log(activeList);
    for(var i=0; i<activeList.length; i++){
        console.log(activeList[i]);
        generateOntoTable(activeList[i]['CardName'], activeList[i]['CardNumber'], activeList[i]['ExpDate'], activeList[i]['SecurityCode'], activeList[i]['Address'], activeList[i]['State']
            , activeList[i]['City'], activeList[i]['ZipCode'], activeList[i]['Phonenumber']);

    }
}

function generateOntoTable(name,number,exp,sec,add,sta,cit,zip,phone){
    var parent = document.getElementById("billingList");
    var row = document.createElement("tr");
    var cardName = document.createElement("td");
    var cardNumber = document.createElement("td");
    var expDate = document.createElement("td");
    var SecurityCode = document.createElement("td");
    var Address = document.createElement("td");
    var State = document.createElement("td");
    var City = document.createElement("td");
    var ZipCode = document.createElement("td");
    var phoneNumber = document.createElement("td");
    var btn = document.createElement("button");
    var action = document.createElement("td");

    btn.innerText = "Remove";
    action.appendChild(btn);
    cardName.innerText = name;
    cardNumber.innerText = number.substring(number.length-4,number.length);
    expDate.innerText = exp;
    SecurityCode.innerText = sec;
    Address.innerText = add;
    City.innerText = cit;
    State.innerText = sta;
    ZipCode.innerText = zip;
    phoneNumber.innerText = phone;



    row.appendChild(cardName);
    row.appendChild(cardNumber);
    row.appendChild(expDate);
    //row.appendChild(SecurityCode);
    row.appendChild(Address);
    row.appendChild(City);
    row.appendChild(State);
    row.appendChild(ZipCode);
    row.appendChild(phoneNumber);
    row.appendChild(action);
    parent.appendChild(row);
}

