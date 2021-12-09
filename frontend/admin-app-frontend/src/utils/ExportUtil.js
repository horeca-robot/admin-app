export default {
    convertToCSVString(orders) {
        const parsedJson = this.getExportOrdersObject(orders);

        const heading = Object.keys(parsedJson[0]).join(";");
        const body = parsedJson.map((j) => Object.values(j).join(";")).join("\n");
        return `${heading}\n${body}`;
    },

    getExportOrdersObject(orders){
        return orders.sort((a,b) => (new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime())).map(o => ({
            date: new Date(o.createdAt).toString().slice(0, 24),
            subTotal: `€${o.subTotal.toFixed(2)}`,
            products: this.getProductsString(o.products.map(p => p.name))
        }))

    },

    getProductsString(allProducts){
        let singleProducts = allProducts.filter((item, pos, self) => { return self.indexOf(item) == pos; })
        let products = []

        singleProducts.forEach(a => {
            let amount = 0
            allProducts.forEach(b => { if(a === b){ amount++ }})

            products.push({ name: a, amount: `${amount}x` })
        })

        return products.map((p) => Object.values(p).join(" ")).join(", ")
    },
    
    downloadCSVFile(csvContent){
        var universalBOM = "\uFEFF" //This will allow special charcters.
        var encodedUri = encodeURIComponent(universalBOM + csvContent)
        var link = document.createElement("a");
        link.setAttribute("href", "data:text/csv;charset=utf-8,"+ encodedUri);
        link.setAttribute("download", "order_history.csv");
        document.body.appendChild(link);
        link.click(); //This will download the data file named "order_history.csv".
        document.body.removeChild(link);
    }
}

