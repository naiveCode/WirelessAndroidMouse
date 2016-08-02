// import the module 
var mdns = require('mdns');
console.log("MDNS Start");
 
// advertise a workstation on port 4321 
//var ad = mdns.createAdvertisement(mdns.tcp('_workstation'), 4321);
var ad = mdns.createAdvertisement(mdns.tcp('_dremote'), 4321);
ad.start();
 
// watch all workstations 
//var browser = mdns.createBrowser(mdns.tcp('_workstation'));
var browser = mdns.createBrowser(mdns.tcp('_dremote'));
browser.on('serviceUp', function(service) {
  console.log("service up: ", service);
});
browser.on('serviceDown', function(service) {
  console.log("service down: ", service);
});
browser.start();
 
// discover all available service types 
var all_the_types = mdns.browseThemAll(); // all_the_types is just another browser...
