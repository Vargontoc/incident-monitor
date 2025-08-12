import { defineStore } from "pinia";

export const useToast = defineStore('toast', {
    state:() => ({ msg: '', type: '', open: false }),
    actions: {
        show(msg: string, type = 'info')  {
            this.msg = msg;
            this.type = type;
            this.open=true;
            setTimeout(() => this.open = false, 2500);
        }
    }
})