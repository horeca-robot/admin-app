export default {
    getTextColor() {
        const style = getComputedStyle(document.body);
        const secondary = style.getPropertyValue('--secondary-color');

        let rgb;
        if(secondary.startsWith('#')){
            rgb = this.hexToRgb(secondary)
        }
        else{
            rgb = secondary.match(/\d+/g);
        }

        let textColor;
        //https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
        if ((rgb[0] * 0.299 + rgb[1] * 0.587 + rgb[2] * 0.114) > 186) {
            textColor = 'rgba(0, 0, 0, 1)';
        } else {
            textColor = 'rgba(255, 255, 255, 1)';
        }

        document.documentElement.style.setProperty('--text-color', textColor);
    },

    //https://stackoverflow.com/questions/5623838/rgb-to-hex-and-hex-to-rgb
    hexToRgb(hex) {
        var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? [
          parseInt(result[1], 16),
          parseInt(result[2], 16),
          parseInt(result[3], 16)
         ] : null;
      }
}