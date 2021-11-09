<template>
    <form class="backpanel" @submit="onSubmit">
        <div class="container">
            <div class="leftBlock">
                <div class="blocks">
                    <label class="text"> *Title:</label>
                    <input class="inputs" placeholder="Product Name" v-model="name" name="name"/>
                </div>
                <div class="blocks">
                <label class="text"> Description:</label>
                <textarea class="textareas" v-model="description"/>
                </div>
                <div class="blocks">
                    <label class="text"> *Price:</label>
                    <input class="inputs" type="number" placeholder="Price" name="price" v-model="price" step="any"/>
                </div>
                <div class="blocks">
                    <label class="text"> Discount price:</label>
                    <input class="inputs" type="number" placeholder="Discount price" name="discountPrice" v-model="discountPrice" step="any"/>
                </div>
                <div class="block-contains-alcohol">
                    <label class="text"> Contains Alcohol:</label>
                    <input class="checkbox" type="checkbox" name="alcohol" v-model="alcohol"/>
                </div>
                <div class="blocks imageComponent">
                    <!-- TODO Place image component here -->
                    <label class="text"> Image:</label>
                    <div class="imageCom">
                        <input type="file" id="img" name="img" accept="image/*" @change="FileSelected"/>
                    </div>
                </div>
            </div>
            <div class="rightBlock"> 
                <div class="blocks">
                    <label class="text"> Ingredients:</label>
                    <label class="extraLabel"/>
                </div>
                <div class="blocks-row">
                    <input class="inputs inputsExtra" type="text" name="ingredients" />
                    <button class="button">Add</button>
                </div>
                <div class="blocks">
                    <label class="text"> Tags:</label>
                    <label class="extraLabel"/>
                </div>
                <div class="blocks-row">
                    <input class="inputs inputsExtra" type="text" name="tags" />
                    <button class="button">Add</button>
                </div>
                <div class="blocks">
                    <label class="text"> Select Categories:</label>
                    <div class="categorieHolder"> 
                        <!-- TODO Load all made categories -->
                        
                    </div>
                </div>
                <div class="blocks-row-buttons">
                    <input class="button formbuttonSave" type="submit" value="Save"/>
                    <input class="button formbuttonDelete" type="submit" value="Delete"/>
                </div>
                
            </div>
        </div>
    </form>
</template>

<script>
export default {
    name: 'AddProduct',
    data(){
        return{
            id: '',
            name: '',
            img: '',
            price: 0,
            discountPrice: 0, 
            description: '', 
            alcohol: true,
        }
    },
    methods:{
        onSubmit(e){
            e.preventDefault()

            if(!this.name || !this.price){
                alert('Not all required fields are filled in, please check again')
                return
            }

            const newProduct = {
                name: this.name,
                price: this.price,
                discountPrice: this.discountPrice,
                description: this.description,
                containsAlcohol: this.alcohol,
                image : this.img
            }

            this.$emit('add-product', newProduct)

            this.resetValues()

        },

        resetValues(){
             this.name = ''
            this.price = 0
            this.discountPrice = 0
            this.description = ''
            this.alcohol = false
        }
    }
}
</script>

<style scoped>
    .backpanel{
        background-color: var(--secondary-color);
        border-color: var(--primary-color);
        border-width: 30px;
        width: 35vw;
        height: fit-content;
        border-radius: 10px;
        padding-bottom: 40px;
        padding-left: 30px;
    }
    .container{
        display: inline-block;
        width: 100%;
    }

    .leftBlock{
        float:left;
        width: 45%;
        min-height: 10px;
    }

    .rightBlock{
        float:right;
        width: 45%;
        min-height: 10px;
    }

    .text{
        font-size: 1.5rem;
        margin-bottom: 2px;
    }


    .blocks{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: column;
    }

    .blocks-row{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: row;
    }

    .block-contains-alcohol{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .blocks-row-buttons{
        margin-top:20px;
        margin-left: -5%;
        display: flex;
        flex-direction: row;
    }

    .button{
        width: 50px;
        background-color: var(--primary-color);
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        color: white;
        margin-left: 1%;
        font-size: 1em;
        font-family: inherit;
    }

        .button:hover{
            cursor: pointer;
        }

    .formbuttonSave{
        margin-left: 10%;
        height: 3vh;
        width: 5vw;
        font-size: 1rem;
    }

    input[type=checkbox] {
        transform: scale(1.3);
    }

    .formbuttonDelete{
        margin-left: 10%;
        height: 3vh;
        width: 5vw;
        background-color: white;
        color: var(--primary-color);
        font-size: 1rem;
    }

    .categorieHolder{
        padding: 10px;
        height: 150px;
        width: 200px;
        overflow: auto;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
    }

    .categories{
        float: left;
        height: auto;
        width: 100%;
    }

    .textareas{
        font-family: inherit;
        resize: none;
        border-radius: 5px;
        border: 2px solid var(--primary-color);
        width: 220px;
        height: 80px;
    }

    .extraLabel{
        width: 220px;
        display: block;
        min-height: 80px;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
    }

    .textareas:focus{
        outline:none;
    }

    .checkboxes{
        background-color: blue;
    }

    .inputs{
        font-family: inherit;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        width: 220px;
        height: 30px;
    }

    .imageComponent{
        height: 300px;
    }

    .imageCom{
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        height: 300px;
    }

    .inputsExtra{
        width:160px;
    }

    .inputs:focus{
        outline: none;
    } 
    
</style>
