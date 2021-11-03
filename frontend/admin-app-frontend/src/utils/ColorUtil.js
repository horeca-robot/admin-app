export default {
    getTextColor() {
        const style = getComputedStyle(document.body);
        const secondary = style.getPropertyValue('--secondary-color');

        //Get rgb values from secondary variable
        var rgb = secondary.match(/\d+/g);

        console.log(rgb[0]);

        let textColor;
        //https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
        if ((rgb[0] * 0.299 + rgb[1] * 0.587 + rgb[2] * 0.114) > 186) {
            textColor = 'rgba(0, 0, 0, 1)';
        } else {
            textColor = 'rgba(255, 255, 255, 1)';
        }

        document.documentElement.style.setProperty('--text-color', textColor);
    }
}