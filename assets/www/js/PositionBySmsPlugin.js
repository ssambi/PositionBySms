var PositionBySmsPlugin = function() {
};


PositionBySmsPlugin.prototype.start = function(acceptedPhoneNumbers, successCallback, failureCallback) {
 return PhoneGap.exec(
		 successCallback, 
		 failureCallback, 
		 'PositionBySmsPlugin',  
		 'START',	
		 [acceptedPhoneNumbers]);
};
PositionBySmsPlugin.prototype.stop = function(successCallback, failureCallback) {
 return PhoneGap.exec(    
		 successCallback,
		 failureCallback,
		 'PositionBySmsPlugin',
		 'STOP',
		 []); 
};
 
PhoneGap.addConstructor(function() {
       PhoneGap.addPlugin("positionbysmsplugin", new PositionBySmsPlugin());
});