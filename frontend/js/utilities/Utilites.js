class Utilites {
    static popupError(e) {
        
    }
    
    static setupFooterContent(e) {
        const footer = document.querySelector("footer"),
              textFooterP = document.createElement("p");

        textFooterP.innerText = "Contato : Help-pet@gmail.com";

        footer.appendChild(textFooterP);
    }
}