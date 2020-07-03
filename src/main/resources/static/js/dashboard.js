$("#signOut").click(function (e) {
	e.preventDefault();
	localStorage.removeItem("accessToken");
	localStorage.removeItem("name");
	window.location = "/view/auth";
});

$(document).ready(function () {
	if (localStorage.getItem("accessToken") == null)
		window.location = "/view/auth";
	else if (localStorage.getItem("name") != null) {
		$("#username").text("Hello, " + localStorage.getItem("name"));
	}
});

const roleValueTransformer = (role) => {
	if (role.startsWith("ROLE_")) {
		role = role.substr(5).toLowerCase();
		role = role.replace("_", " ");
		return role.charAt(0).toUpperCase() + role.slice(1);
	} else {
		role = role.toUpperCase();
		role = role.replace(" ", "_");
		role = "ROLE_" + role;
		return role;
	}
};
