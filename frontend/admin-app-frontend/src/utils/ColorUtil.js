export default {
    getTextColor(){
        const style = getComputedStyle(document.body)
        const secondary = style.getPropertyValue('--secondary-color')

        var rgb = secondary.match(/\d+/g); //Get rgb values from --secondary-color variable

        if((rgb[0]*0.299 + rgb[1]*0.587 + rgb[2]*0.114) > 186){
            document.documentElement.style.setProperty('--text-color', 'rgba(0, 0, 0, 1)');
        }
        else{
            document.documentElement.style.setProperty('--text-color', 'rgba(255, 255, 255, 1)');
        }
    }
}