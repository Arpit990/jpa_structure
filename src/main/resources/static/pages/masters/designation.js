var formId = "#designationForm";

$(document).ready(() => {

	// Custom validation methods
	$.validator.addMethod("lettersonly", function(value, element) {
		return /^[a-zA-Z\s]+$/.test(value);
	}, "Letters only please");

	validateDesignatinForm(formId);
});

function validateDesignatinForm(form) {
	$(form).validate({
		errorClass: "text-danger",
		
		rules: {
			designation: {
				required: true,
				lettersonly: true
			},
			description: {
				required: true,
				lettersonly: true
			}
		},

		messages: {
			designation: {
				required: "Please enter a designation",
				lettersonly: "Designation should contain only letters"
			},
			description: {
				required: "Please enter a description",
				lettersonly: "Description should contain only letters"
			}
		},

		errorPlacement: function(error, element) {
			error.insertAfter(element);
		},

		// Highlight and unhighlight error fields
		highlight: function(element) {
			$(element).addClass('error-field');
		},
		unhighlight: function(element) {
			$(element).removeClass('error-field');
		},

		submitHandler: function(form) {
			// Form submission logic
			alert('Form is valid! Submitting...');
			form.submit();
		}
	});
}