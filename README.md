# ToDoList
Here is a simple To Do list app .. using Room Database in Kotlin

UI walkthrough :

the main screen is a List of all added items

![IMG_DDD0B3ECB384-1](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/3fa51fd4-c9ba-4932-8544-0c45df88738c)

showing retrived data from the created DB are desplayed by RecyclerView.

bu clicking the floating "+" buttion -> add item :
![IMG_D2A68AF71832-1](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/8cdbadfb-57f4-49f6-9331-35cd0c770545)


Also by clicking an item .. user will be navigated to the item's details screen.
![IMG_DDD0B3ECB384-2](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/8776e632-b0db-480c-ba9b-ccb1c06e6d5f)

displaying the name and details of selected item ,whether the item is done or not,the date enterd,and two action buttons .. "Done" button  for marking the item when user have done the task -> mark it as (Done !!), which would trigger the button to be disabled and a helpping question will appear to ask if user want to mark it as (ToDo) again.

![IMG_DDD0B3ECB384-3](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/74bd3e6e-df9b-4f26-b068-0b8f484b5436)

![IMG_DDD0B3ECB384-5](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/af6b264e-8aed-425c-bd9c-13ee5e0ac865)

"delete" button will remove the item from DB and the list .. deleting "Dentist" item :
![IMG_DDD0B3ECB384-8](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/824cfa8b-05b8-4194-b2a6-8fcfc1c496c8)

a confirmation dialog will appear
![IMG_DDD0B3ECB384-9](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/520361f8-24a6-4439-b535-da18d2560468)

the list before deletion :
![IMG_DDD0B3ECB384-6](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/805eb4ba-76c7-446f-8936-d1e8a455902a)

after :
![IMG_DDD0B3ECB384-10](https://github.com/ShahadAljalajel/ToDoList/assets/138501486/b739e26b-59c3-4617-a91b-f287dfaabbeb)

