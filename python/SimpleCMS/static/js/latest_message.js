$(document).ready(function(){

	var duration = 10000; // Sets how often to refresh i ms so 5000 ms = 5 seconds


	var oldMessage; // Unused variables atm
	var newMessage; // Unused variabled atm

	// Turn off latest message by giving a "norefresh" parameter in the URL.
	try {
		if (location.search.indexOf("norefresh") != -1 ||
			localStorage.norefresh) {
			localStorage.norefresh = "1";
			return;
		}
	} catch (e) { }


	function update(){


		var response = $.ajax({
			url:		"http://www.wussap.com/ajax/latest_message.php",
			type:		"POST",
			cache:		false,
			async:		false,
			data:		({id : oldMessage}), // I have to make some sort of check here to send the id of the messege displayed so
											//  that it don't update when there is no new message
			success:	function(msg){
				$("#header .latest_message").fadeOut("slow", function(){
					$("#header .latest_message").html(msg);
				});

				$("#header .latest_message").fadeIn("slow");

			}
		});

	}

	setInterval(update, duration);

});
