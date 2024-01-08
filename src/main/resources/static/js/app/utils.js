async function removeChildrens(element) {
    while (element.firstChild) {
        element.removeChild(element.lastChild);
    }
}