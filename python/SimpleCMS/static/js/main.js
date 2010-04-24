$(document).ready(function(){
	
	
	jQuery.fn.center = function (absolute) {
	    return this.each(function () {
	        var t = jQuery(this);

	        t.css({
	            position:    absolute ? 'absolute' : 'fixed', 
	            left:        '50%', 
	            top:        '50%', 
	            zIndex:        '99'
	        }).css({
	            marginLeft:    '-' + (t.outerWidth() / 2) + 'px', 
	            marginTop:    '-' + (t.outerHeight() / 2) + 'px'
	        });

	        if (absolute) {
	            t.css({
	                marginTop:    parseInt(t.css('marginTop'), 10) + jQuery(window).scrollTop(), 
	                marginLeft:    parseInt(t.css('marginLeft'), 10) + jQuery(window).scrollLeft()
	            });
	        }
	    });
	};
	

	// Hide all things that should be hidden on page.load

	$(".surftrain_info").hide();



	// Surftrain departure info slides up/down

	$(".row .plus_minus a").click(function(){

		//Get the plus or minus sign picture
		var pic_src  = this.getElementsByTagName("img")[0].getAttribute("src");

		// Check to see wich sign i showing and slideUp or Down depending on result
		if (pic_src == "gfx/plus.jpg") {
			$(this).parents(".row").children("div.surftrain_info").slideDown("fast");
			this.getElementsByTagName("img")[0].setAttribute("src", "gfx/minus.png");
		}else{
			$(this).parents(".row").children("div.surftrain_info").slideUp("fast");
			this.getElementsByTagName("img")[0].setAttribute("src", "gfx/plus.jpg");
		}

		// deactivate default behaviour on the <a> Tag
		return false;
	});

	// Same as above but the link instead
	$(".row .name a").click(function(){

		var pic_src = $(this).parents()[3].getElementsByTagName("img")[0].getAttribute("src");

		if(pic_src == "gfx/plus.jpg"){
			$(this).parents(".row").children("div.surftrain_info").slideDown("fast");
			$(this).parents()[3].getElementsByTagName("img")[0].setAttribute("src", "gfx/minus.png");
		}else{
			$(this).parents(".row").children("div.surftrain_info").slideUp("fast");
			$(this).parents()[3].getElementsByTagName("img")[0].setAttribute("src", "gfx/plus.jpg");
		}

		return false;

	});
	
$("#forgot_password").click(function(){
		
		var popup = "<div id='popup'><p class='floatright'><a href='#' id='close_popup'>Close</a></p><div id='popup_top'></div><div id='popup_main'><div class='content'></div></div><div id='popup_bottom'></div></div>";

		var text = "<p class='text'><strong>Help I forgot my password</strong></p>";
		text += "<p class='text'>1) Start the Wussap sidebar and click the Forgot password link.</p>";
		text += '<img src="gfx/forgot_password.jpg" alt="" style="padding:5px 5px 5px 5px;border:1px dotted #cccccc;margin:0 0 10px 10px;" />';
		text += '</div><p class="text">2) Enter your Wussap name and click on the OK button</p>';
		text += '<img src="gfx/retrieve_password.jpg" alt="" style="padding:5px 5px 5px 5px;border:1px dotted #cccccc;margin:0 0 0 10px;" />';
		text += '<br /><br />';
		text += '<p class="text">An email will be sent to your registered email with a new password.</p>';
		text += '<p class="text">For further information or questions please use our <a href="use.php?page=feedback">feedback form</a>.</p>';
		text += '<p class="text">/ The Wussap Team</p>';
		
		$("body").append(popup);
		
		$("#popup").center();
		$("#popup").css("visibility", "visible");
		$("#popup").hide();
		$("#popup").fadeIn("slow", function(){
			$("#popup_main .content").append(text);
		});
		
		$("#close_popup").click(function(){
			$("#popup").fadeOut("slow", function(){
				$("#popup").remove();
			});
		});
		
		return false;
	});
		
});


function checkStatus(checkbox){
	if(checkbox.checked == true){
		//alert("checked");
		FB.Connect.requireSession()
	}
	//FB.Connect.requireSession()
}




function FacebookPublish(message, attachment, action_links) {
	FB_RequireFeatures(["Connect"], function() {
		FB.Facebook.init(FBAPIKey, "xd_receiver.htm");

		// Add default values
		attachment.href = attachment.href || "http://www.wussap.com/";
		if (!attachment.media) {
			attachment.media = [{
				"type": "image",
				"src": "http://www.wussap.com/gfx/small_logo.jpg",
				"href": "http://www.wussap.com/"
			}];
		}
		if (!action_links || action_links.length == 0) {
			action_links = [{
				"text": "Join Wussap",
				"href": "http://www.wussap.com/"
			}];
		}
		FB.Connect.streamPublish(message, attachment, action_links);
	});
}

function FacebookUpdateVisibility() {
	FB.Connect.ifUserConnected(function(uid) {
		$(".fb_visible_loggedin").show("fast");
		$(".fb_visible_loggedout").hide("fast");
	}, function() {
		$(".fb_visible_loggedin").hide("fast");
		$(".fb_visible_loggedout").show("fast");
	});
}

function FacebookInit() {
	$(document).ready(function() {
		FB_RequireFeatures(["XFBML"], function() {
			FB.Facebook.init(FBAPIKey, "xd_receiver.htm");
			FacebookUpdateVisibility();
		});
	});
}
