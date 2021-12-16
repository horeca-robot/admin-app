import languages from '../assets/languages/languages.json'

export default{
    getTextObject()
    {
        const language = localStorage.getItem('language')
        return languages[language]
    }
}