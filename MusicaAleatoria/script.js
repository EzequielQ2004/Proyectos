// Array de canciones de prueba (podés reemplazar estas URLs por otras válidas)
const canciones = [
  {
    titulo: "Canción de prueba 1",
    artista: "Artista 1",
    url: "c:\Users\Quiro\Downloads\Rise Above the Flames.mp3",
    spotify: "#",
    youtube: "#"
  },
  {
    titulo: "Canción de prueba 2",
    artista: "Artista 2",
    url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
    spotify: "#",
    youtube: "#"
  },
  {
    titulo: "Canción de prueba 3",
    artista: "Artista 3",
    url: "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
    spotify: "#",
    youtube: "#"
  }
];

// Elementos del DOM
const btnIniciar = document.getElementById("btn-iniciar");
const inicioContainer = document.getElementById("inicio-container");
const reproductorContainer = document.getElementById("reproductor-container");
const audioPlayer = document.getElementById("audio-player");
const audioSource = document.getElementById("audio-source");
const tituloCancion = document.getElementById("titulo-cancion");
const artistaCancion = document.getElementById("artista-cancion");
const linkSpotify = document.getElementById("link-spotify");
const linkYoutube = document.getElementById("link-youtube");

// Función para elegir una canción aleatoria y cargarla
function reproducirCancionAleatoria() {
  const aleatoria = canciones[Math.floor(Math.random() * canciones.length)];

  audioSource.src = aleatoria.url;
  audioPlayer.load();  // recarga el <audio> con la nueva fuente
  audioPlayer.play();  // comienza la reproducción

  tituloCancion.textContent = aleatoria.titulo;
  artistaCancion.textContent = aleatoria.artista;
  linkSpotify.href = aleatoria.spotify;
  linkYoutube.href = aleatoria.youtube;
}

// Evento al hacer clic en "Descubrí una canción"
btnIniciar.addEventListener("click", () => {
  inicioContainer.style.display = "none";
  reproductorContainer.style.display = "block";
  reproducirCancionAleatoria();
});