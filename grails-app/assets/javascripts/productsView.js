$(document).ready(function() {
	getProducts();
	$('#purschase').click(purchase);

});

// Finish the purschase
var purchase = function() {
	var saleProducts = [];
	// Recover the information selected by the user
	$('.quantity').each(function(index, e) {
		var value = parseInt(e.value);
		if (e.value > 0) {
			saleProducts.push({
				product : e.parentElement.parentElement.productId,
				quantity : value
			});
		}
	})
	// Send a request to the server
	$.ajax('/sales', {
		'data' : JSON.stringify(saleProducts),
		'type' : 'POST',
		'processData' : false,
		'contentType' : 'application/json',
		'success' : function(data) {
			alert("Purchase completed successfuly");
			getProducts();
		},
		'error' : function(data) {
			var dataObject = JSON.stringify(data.responseJSON);
			errors = "Errors:\n"
			if (data.forEach) {
				// More than 1 error
				dataObject.forEach(function(e) {
					errors += e + "\n";
				})	
			} else {
				errors += dataObject;
			}
			alert(errors);
		}
	});
}


var getProducts = function() {
	$.get('/products', null, function(data) {
		// Clear the table before update
		$('#productsTable').html("");
		data.forEach(function(product) {
			var container = document.createElement('tr');
			container.productId = product.id;
			addTh(product.name, container);
			addTh(product.description, container);
			addTh(product.quantity, container);
			addTh('<input type="text" class="quantity" value="0" />', container)
			$(container).appendTo('#productsTable');
		});
	}, 'json');
}

var addTh = function(value, parent) {
	var th = document.createElement('th');
	$(th).html(value);
	$(parent).append(th);
}