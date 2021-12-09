<template>
    <div id="data">
        <div id="bar" :style="`height: var(--${day.name});`" @mouseover="selectDay()"/>
        <label>{{ day.name }}</label>
    </div>
</template>
<script>
export default {
    emits: ['onHover'],
    props: {
        day: Object,
        highest: Number
    },
    watch: {
        day(){
            this.determineHeight()
        },
        highest(){
            this.determineHeight()
        }
    },
    created() {
        this.determineHeight()
    },
    methods: {
        determineHeight(){
            document.documentElement.style.setProperty(`--${this.day.name}`, `${(this.day.orders / this.day.amount / this.highest * 315)}px`)
        },
        selectDay(){
            this.$emit('onHover', this.day)
        }
    }
}
</script>
<style scoped>
    #data{
        display: flex;
        flex-direction: column;
        height: 100%;
        justify-content: flex-end;
    }

    #bar{
        background-color: var(--primary-color);
        opacity: 0.75;
        margin-bottom: 20px;
        min-height: 3px;
    }

        #bar:hover{
            opacity: 1;
        }

    label{
        transform: rotate(90deg);
        color: var(--text-color);
        width: 45px;
        display: flex;
        justify-content: flex-start;
        margin-bottom: 20px;
        font-size: 1.2em;
    }
</style>