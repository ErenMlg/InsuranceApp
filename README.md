# Insurance App
<br>
<p align="center">I voluntarily participated in the internship program initiated by Ada Software. During this process, each intern was asked to create an insurance application. I developed mine using Jetpack Compose. The project primarily demonstrates the use of MVVM, Coroutines, Flows, and related technologies.</p>
 <br>

<p align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img src="https://img.shields.io/badge/License-Apache%202.0-red.svg"></a>
<a href="https://android-arsenal.com/api?level=23"><img src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"></a>
<a href="https://github.com/ErenMlg"><img src="https://img.shields.io/badge/github-ErenMlg-blue"></a>
</p>

 ## Project Screens
![Screenshot_1](https://github.com/user-attachments/assets/75cf5beb-3956-43a5-95f9-32a48e394541)

## Project Tech Stack
<ul>
 <li>This project developed with %100 with <a href="https://developer.android.com/kotlin?hl=tr">Kotlin</a></li>
 <li>Made with <a href="https://developer.android.com/topic/architecture?hl=tr">Android Architecture Components</a> for the Collection of libraries that help you design robust, testable, and maintainable apps.</li>
 <li><a href="https://developer.android.com/topic/libraries/architecture/viewmodel?hl=tr">ViewModel</a>: The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic. Its principal advantage is that it caches state and persists it through configuration changes. This means that your UI doesn’t have to fetch data again when navigating between activities, or following configuration changes, such as when rotating the screen.</li>
  <li><a href="https://developer.android.com/kotlin/coroutines"">Kotlin Coroutine</a>On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive. Over 50% of professional developers who use coroutines have reported seeing increased productivity. This topic describes how you can use Kotlin coroutines to address these problems, enabling you to write cleaner and more concise app code.</li>
  <li><a href="https://developer.android.com/training/dependency-injection/hilt-android">Dependency Injection with Hilt</a>: Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies.</li>
  <li><a href="https://developer.android.com/guide/navigation">Navigation</a>: Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.</li>
  <li><a href="https://square.github.io/retrofit/">Retrofit</a>: Retrofit is the class through which your API interfaces are turned into callable objects. By default, Retrofit will give you sane defaults for your platform but it allows for customization.</li>
  <li><a href="https://kotlinlang.org/docs/ksp-overview.html">Kotlin KSP</a>: Kotlin Symbol Processing (KSP) is an API that you can use to develop lightweight compiler plugins</li>
  <li><a href="https://square.github.io/okhttp/">OkHttp</a>: OkHttp is an open-source HTTP client developed by Square and is widely used for handling network operations in Android apps.</li>
  <li><a href="https://developer.android.com/topic/architecture/data-layer">Repository</a>: This located in data layer that contains application data and business logic. </li>
  <li><a href="https://developer.android.com/topic/architecture/domain-layer?hl=tr">Use Case</a>: Located domain layer that sits between the UI layer and the data layer.</li>
  <li><a href="https://developer.android.com/develop/ui/compose/layouts/pager?hl=tr">Pager</a>: Horizontal pager for swipeable view.</li>
</ul>

## Architecture
This app uses <a href="https://developer.android.com/topic/architecture?hl=tr#recommended-app-arch"> MVVM (Model View View-Model)</a> architecture structure.
<img src="https://camo.githubusercontent.com/fbd92f5206e4fdf48b0827b9aa91211446eed9d9d0e2d2464156fe064b8b4e8c/68747470733a2f2f692e737461636b2e696d6775722e636f6d2f637233516b2e706e67">

## Project Graph
This app uses <a href="https://developer.android.com/topic/architecture?hl=tr#recommended-app-arch">MVVM (Model View View-Model)</a> architecture
Core layer have 5 sub layer as common, data, di, domain, navigation, presentation;
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/1b9be139-c9bf-4f0f-a1af-c47b213c9b29">
    </td>
    <td>
      <ul>
        <li>Extension: Hold Map extensions for the response to entity, other extension for the view, network response, modifier etc.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/daf6fdfb-7c07-44fc-836b-740a86868985"></td>
    <td>
      <ul>
        <li>Api: Hold api services</li>
        <li>Dto: Hold database objects for the response</li>
        <li>Repository: Hold repository for the application data and business logic.</li>
        <li>Source: For the remote data source to listen result and convert flow.</li>
      </ul>
    </td>
  </tr>
    <tr>
    <td><img src="https://github.com/user-attachments/assets/af710f3e-b4aa-4a01-bb69-a792fb8027c1"></td>
    <td>
      <ul>
        <li>Di: Hold di objects and works</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/a03e8ce7-4d28-4fb1-aef0-521c76bd666f"></td>
    <td>
      <ul>
        <li>Model: Hold used models on screens.</li>
        <li>Repository: Hold repository interfaces for the clean view.</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/670c60bf-9eba-4dc2-889c-aac5aae5366b"></td>
    <td>
      <ul>
        <li>Navigation: Hold navhost, route objects and bottom app bar</li>
      </ul>
    </td>
  </tr>
 <tr>
  <td><img src="https://github.com/user-attachments/assets/943c8c61-5434-43af-ab7e-4c210a01c6f2"></td>
 <td>
  Presentation layer have 7 sub layer as components, customer, home, login, payments, policies, splash, theme.
They hold own fragments, viewmodels and detail pages.
 </td>
 </tr>
</table>


## End Note
I may have mistakes, you can contact me for your feedback. 👉 📫 **eren.mollaoglu@outlook.com**<br>

## License
<pre>
Designed and developed by 2024 ErenMlg (Eren Mollaoğlu)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>

