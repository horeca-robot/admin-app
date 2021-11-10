<template>
    <div>
        <img :src="image">
    </div>
</template>

<script>
import image from '../images/background.jpg'
import api from '../wrappers/InfoWrapper.js'

export default {
    name: 'Background',
    data() {
        return {
            image: image
        }
    },
    async mounted(){
        this.update()
    },
    methods: {
        async update () {
            let response = await api.getWebsiteSettings()

            let websiteSettings  = response.data.data.info
                
            let base64 = websiteSettings.backgroundImage

            if(base64)
                this.image = base64
        }
    }
}
</script>

<style scoped>
    div{
        position: fixed;
        z-index: -1;
    }

    img{
        width: 100vw;
        height: 100vh;
    }
</style>