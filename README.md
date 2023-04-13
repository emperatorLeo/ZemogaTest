# ZemogaTest
## Zemoga code challenge

### Post List Fragment
The first time you run the app , you´ll see a loader giving you feedback that an api call has been made and we are waiting the answer , then you'll see the a populated posts list: 

<img src="https://user-images.githubusercontent.com/23562934/230835764-d053c501-ab2b-42c5-b941-0bc3516f5b8a.jpg" width="280">

### here there are some things you can do:

* tap on the start to add a post to your favorites and you'll see that post on top of the list

* tap on the trash icon to delete that post

* if you have some favorites posts then you click on "DELETE ALL POSTS" button , you'll see that only the favorite ones remain in the list

* if you tap on any post you can acces to the details fragment 

the reload posts from the api button is only visible when you tap on "DELETE ALL POSTS" first

<img src="https://user-images.githubusercontent.com/23562934/230837238-548552c1-8f0e-46ff-98a4-0080f8a8cfc3.jpg" width="200">

### Details fragment
You can only acces to this fragment once you tap on a post, here you could see all releated post details besides a list of comments to that post

In the very bottom of the screen you'll see the "Previous" button to go back to the previous fragment

<img src="https://user-images.githubusercontent.com/23562934/230841599-4dbc74a2-5090-4828-8c18-b27f325bcfb1.jpg" width="200">

## Proposed approach 

* Clean Architecture
* MVVM Architecture : through this architecture we can have a reactive screen always listenig to data changes from the ViewModel
* Coroutines: to do the api call on a background thread
* MutableLiveData: to have reactive screens
* Mockk: to create native and easier unit tests 

