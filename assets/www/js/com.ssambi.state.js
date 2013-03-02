var com = com ? com : {};
com.ssambi = com.ssambi ? com.ssambi : {};

com.ssambi.CHANGED_EVENT = 'CHANGED_EVENT';

com.ssambi.state = function() {
	
	var _active = false;
	var _phoneNumbers = [];

	return {
		isActive: function() {
			return _active;
		},
		setActive: function(v) {
			_active = v;
			
			$(this).trigger(com.ssambi.CHANGED_EVENT, 'active');
		},
		getPhoneNumbers: function() {
			return _phoneNumbers;
		},
		addPhoneNumber: function(pn) {
			_phoneNumbers.push(pn);
			
			$(this).trigger(com.ssambi.CHANGED_EVENT, 'phoneNumbers');
		},
		removePhoneNumber: function(index) {
			_phoneNumbers.splice(index, 1);
			
			$(this).trigger(com.ssambi.CHANGED_EVENT, 'phoneNumbers');
		},
		
		getStorageObj: function() {
			return {
				active: _active,
				phoneNumbers: _phoneNumbers
			};
		},
		loadFromStorageObj: function(obj) {
			_active = obj.active;
			_phoneNumbers = obj.phoneNumbers;
		}
	}
}();