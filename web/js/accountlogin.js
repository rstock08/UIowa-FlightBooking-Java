/**
 * Created by johnn on 3/8/2017.
 */
/**
 * Button for logging into the website
 * @type {Element}
 */
var button = document.getElementById("loginButton");

/**
 * Button used for creating a new account
 * @type {Element}
 */
var create = document.getElementById("createAccountButton");

/*
    When button is clicked it calls the functions below. Essentially,
    it checks the username and password in database.
    If the combination is in the database, then it initiates the login process.
 */
button.onclick = function(){
    var username = document.getElementById("loginUsername");
    var password = document.getElementById("loginPassword");
    if(checkLogIn(username, password)){
        loginProcess(username, password);
    }
};

/*
 * When button is clicked calls the functions below.  Essentially, it checks the
 * username and password to see if they are valid suggestions for a new account.
 * If both are valid then creates the account - otherwise does not.
 */
create.onclick = function(){
	var username = document.getElementById("loginUsername");
	var password = document.getElementById("loginPassword");
	
	if(checkCreateAccount(username, password) ){
		createProcess(username, password);
	}
	
}

/*
    Checks if the username and password combination are in the database.
    First checks username. If it is in the system, then checks the entered password.
    If a field is incorrect it will highlight the text field in light red. If it is
    correct it will highlight in light green. This is just for testing purposes.

 */
function checkLogIn(username, password){
    if(checkUsername(username.value)){
        username.style.backgroundColor = "#A0FFA0";

        if(checkPassword(username.value, password.value)){
            password.style.backgroundColor = "#A0FFA0";
            return true;
        } else {
            password.style.backgroundColor = "#F3A0A0";
        }

    } else {
        username.style.backgroundColor = "#F3A0A0";
    }

    return false;
}

/*
    Checks database for username. Clearly needs more work.
 */
function checkUsername(username){

    return username == "johnny35";

}

/*
    Checks database to make sure password goes with name. Clearly needs more work
 */
function checkPassword(username, password){

    // can't do username.password in this case sadly.
    return password == "Hollow13"; // the password associated with johnny35 account info.

}

/*
 * Checking hierarchy with color showing when username and password combos are valid
 */
function checkCreateAccount(username, password){
	if(checkCreateUsername(username.value)){
        username.style.backgroundColor = "#A0FFA0";

        if(checkCreatePassword(password.value)){
            password.style.backgroundColor = "#A0FFA0";
            return true;
        } else {
            password.style.backgroundColor = "#F3A0A0";
        }

    } else {
        username.style.backgroundColor = "#F3A0A0";
    }

    return false;
}

/*
 * Checks to see if the username is a valid email address.  If it is returns true, if not returns false;
 */
function checkCreateUsername(username){

	var goodEmail = "bob@air.com"; // temporary email.
	/*  Checks if username is an email address or not  */
	
	return username == goodEmail
}

/*
 * Checks to make sure password follows regulations.
 */
function checkCreatePassword(password){
		
	if(password.length>=8 && /[A-Z]/.test(password)  && /[a-z]/.test(password) && /[0-9]/.test(password)){
		alert("Password is valid");
		return true;
	}else{
		alert("Password is NOT valid.  Please fix: More than 8 characters, 1 upper, 1 lower, 1 number");
		return false;
	}	
}

