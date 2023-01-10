// TO MAKE THE EXAMPLE WORK YOU MUST
// ADD YOUR ACCESS TOKEN FROM
// https://account.mapbox.com
const ACCESS_TOKEN = 'pk.eyJ1Ijoic29rb2wzNzgzIiwiYSI6ImNsYnl5eHNuaDExaXQzcXAzdWoyZWtncXIifQ.lFJFZjk9xzCDMFuPqwxS7A';
const DISTANCE_QUERY = 'https://api.mapbox.com/directions-matrix/v1/mapbox/driving/';

let departure = [];
let destination = [];

const script = document.getElementById('search-js');
script.onload = () => {
    const elements = document.querySelectorAll('mapbox-address-autofill');
    for (const autofill of elements) {
        autofill.accessToken = ACCESS_TOKEN;

        autofill.addEventListener('retrieve', (event) => {
            controlDistance(event)
        })
    }
};

function controlDistance(event) {

    if (event.currentTarget.className == 'destination') {
        destination = event.detail.features[0].geometry.coordinates;
    } else if (event.currentTarget.className == 'departure') {
        departure = event.detail.features[0].geometry.coordinates;
    }
    if (departure.length > 0 && destination.length > 0) {
        findDistance();
    }
}

function findDistance() {
    let locationsDeparture = departure[0] + ',' + departure[1] + ';';
    let locationsDestination = destination[0] + ',' + destination[1] + '?';
    let url = DISTANCE_QUERY + locationsDeparture + locationsDestination + 'access_token=' + ACCESS_TOKEN;
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false for synchronous request
    xmlHttp.send(null);
    parseDistance(xmlHttp.responseText);
}

function parseDistance(responseText) {
    let parse = JSON.parse(responseText), totalDistance;
    var elemDistance = document.getElementById('distance');
    totalDistance = 0;
    if (parse.hasOwnProperty("destinations")) {
        let destArr = parse.destinations;
        let totalDistance = parseInt('0');
        for (let i = 0; i < destArr.length; i++) {
            totalDistance += parseInt(destArr[i].distance);
        }
        elemDistance.value = totalDistance;
    }
    countCost();
}

function countCost() {
    const elemDistance = document.getElementById('distance');
    const elemCost = document.getElementById('cost');
    if (elemDistance.value > 0) {
        let price = findPriceCategory()
        elemCost.value = price * elemDistance.value;
    } else {
        elemCost.value = 0;
    }
}

function findPriceCategory() {
    const elemCarCategory = document.getElementById('car-category');
    const mapCategory = getMapFromPrice();
    let price = 1;
    if (elemCarCategory != null) {
        mapCategory.forEach(s => {
            let keyValue = s.split('=');
            if (keyValue[0] == elemCarCategory.value) {
                price = keyValue[1];
            }
        })
    }
    return price;
}

function getMapFromPrice() {
    let string = document.getElementById('prices').value;
    let strings = string.split(',');
    return strings;
}