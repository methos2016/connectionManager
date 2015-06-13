var connectionManager = {
	createEvent: function(successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
            errorCallback,
			'ConnectionManager',
            'test', 
           	[{ }] 
		);
	},
	createSocket: function(name, address, port, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'createSocket', 
           	[{ "name":name, "address":address, "port":port}] 
		);
	},	
	socketSendData: function(name, data, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'socketSendData', 
           	[{ "name":name, "data":data}] 
		);
	},
	socketReadData: function(name, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'socketReadData', 
           	[{ "name":name}] 
		);
	},
	closeSocket: function(name, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'closeSocket', 
           	[{ "name":name }] 
		);
	},
	createServerSocket: function(name, port, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'createServerSocket', 
           	[{ "name":name, "address":address, "port":port}] 
		);
	},	
	serverSocketSendData: function(name, clientName, data, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'serverSocketSendData', 
           	[{ "name":name, "clientName":clientName, "data":data}] 
		);
	},
	serverSocketReadData: function(name, clientName, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'serverSocketReadData', 
           	[{ "name":name, "clientName":clientName}] 
		);
	},
	serverSocketDropClient: function(name, clientName, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'serverSocketDropClient', 
           	[{ "name":name, "clientName":clientName}] 
		);
	},
	closeServerSocket: function(name, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'closeServerSocket', 
           	[{ "name":name }] 
		);
	},
	sendHTTPGet: function(request, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'sendHTTPGet', 
           	[{ "request":request }] 
		);
	},
	sendHTTPPost: function(name, parameters, successCallback, errorCallback) {
		cordova.exec(
			successCallback, 
           	errorCallback, 
			'ConnectionManager',
            'sendHTTPPost', 
           	[{ "request":request, "parameters":parameters }] 
		);
	}
}		
module.exports = connectionManager;
