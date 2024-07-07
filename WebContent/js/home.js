let n_img = document.querySelectorAll('.img-per-slider img').length;
let corrente = 1;
let intervalID;

const slideContainer = document.querySelector('.slider-categories');
const cards = document.querySelectorAll('.slide-category');

let currentIndex = 0;


let prodottoContainer = document.querySelector('.prodotti-inline');

let currentShirt = 0;

function next() {
	clearInterval(intervalID); // Cancella l'intervallo corrente
	let currentImg = document.getElementById('img-' + corrente);
	currentImg.classList.remove('active');
	corrente = (corrente % n_img) + 1;
	let nextImg = document.getElementById('img-' + corrente);
	nextImg.classList.add('active');
	intervalID = setInterval(next, 3500); // Imposta un nuovo intervallo
}

function prec() {
	clearInterval(intervalID); // Cancella l'intervallo corrente
	let currentImg = document.getElementById('img-' + corrente);
	currentImg.classList.remove('active');
	corrente = ((corrente - 2 + n_img) % n_img) + 1;
	let prevImg = document.getElementById('img-' + corrente);
	prevImg.classList.add('active');
	intervalID = setInterval(next, 3500); // Imposta un nuovo intervallo
}



function showNextProdotto() {
	prodottoContainer.scrollBy({left: 300, behavior: "smooth"});
}

function showPreviousProdotto() {
	prodottoContainer.scrollBy({left: -300, behavior: "smooth"});  
}

intervalID = setInterval(next, 3500); // Imposta l'intervallo iniziale