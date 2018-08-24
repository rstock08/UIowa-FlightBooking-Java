/**
 * Created by johnn on 4/20/2017.
 */
$(function(){

    document.getElementById("loginAccountForm").reset();
    document.getElementById("createAccountForm").reset();

    $("#passwordErrorMessage").hide();
    $("#loginErrorMessage").hide();



    $("#loginButton").on("click",function(){
        this.setAttribute("class","selected");
        document.getElementById("createButton").removeAttribute("class");
        document.getElementById("loginAccountForm").reset();
        $("#loginDiv").show();
        $("#createDiv").hide();
        $("#loginErrorMessage").hide();

    });

    $("#createButton").on("click",function(){
        this.setAttribute("class","selected");
        document.getElementById("loginButton").removeAttribute("class");
        document.getElementById("createAccountForm").reset();
        $("#loginDiv").hide();
        $("#createDiv").show();
        $("#passwordErrorMessage").hide();
        $("#youExistDude").hide();

    });

});

function validateForm(){
    var password = document.forms["createAccountForm"]["userPassword"].value;
    var regex = /^(?=.*[^a-zA-Z])(?=.*[a-z])(?=.*[A-Z])\S{8,16}$/;
    if(regex.test(password)){
        $("#passwordErrorMessage").hide();
        userMayExist();
    }else{
        $("#passwordErrorMessage").show();
    }
    return false;
}

function userExists(){
    var data = $("#loginAccountForm").serialize();
    logIn(data);
    return false;
}

function userMayExist(){
    $.post("General.CreateAccount",$("#createAccountForm").serialize(),function(msg){
        console.log(msg);
        if(msg.length>0){
            var data = $("#createAccountForm").serialize();
            if(msg=="true"){
                logIn(data);
                return true;
            }else if(msg=="alreadyExists") {
                alert("You already have an account!\nLogging you in...");
                setTimeout(logIn(data),2000);
            } else {
                $("#youExistDude").show();
                return false;
            }
        }
    });
}

function logIn(data){
    $.post("General.Login", data ,function(msg){
        if(msg.length>0){
            console.log(msg);
            if(msg=="false"){
                $("#loginErrorMessage").show();
            }else{
                window.location.href = msg;
            }
        }
    });
}