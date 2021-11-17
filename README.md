# Pokeflow

Playing with pokemons and unidirectional data flow.

Data is fetched from [PokéAPI](https://pokeapi.co/)

### Architecture
The repo follows the recommended [architecture from Google](https://developer.android.com/jetpack/guide) following the [MVI](https://hannesdorfmann.com/android/mosby3-mvi-1/) architectural presentation pattern using [Uniflow](https://github.com/uniflow-kt/uniflow-kt) . Even if we do not implement Use-Cases we stick as much as possible with the [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) principles.

### UI Layer
Since we are using Uniflow, [Jetpack Compose](https://developer.android.com/jetpack/compose) was the obvious choice rather than standard layout files, because we can seamlessly react to the "STATE" without having observers such as `onState` and Uniflow intergrates perfectly well with it (check [Uniflow and Compose](https://github.com/uniflow-kt/uniflow-kt/blob/master/doc/compose.md)).

### Navigation
The navigation is perfomed with [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) in beta, we could have used the stable [Jetpack Navigation](https://developer.android.com/guide/navigation), but this would have meant mixing fragments with Compose adding complexity that we wanted to avoid for a this playground project.

### MVI framework
While we could have re-implemented the [MVI logic manually](https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d), it would have been a lot of boilerplate so we can pick one ot the existing frameworks, for example:
- [Uniflow](https://github.com/uniflow-kt/uniflow-kt)
- [Orbit-mvi](https://github.com/orbit-mvi/orbit-mvi)
- [MvRx](https://github.com/airbnb/mavericks)

We went with Uniflow since it is a compromise between the complexity and the amount of boilerplate code that needs to be written. Two things that we are not fond of:
1. Uniflow uses [`observeAsState()`](https://developer.android.com/jetpack/compose/state#use-other-types-of-state-in-jetpack-compose). This means that we depend on LiveData i.e. from the Android Framework. The library could use Flow instead as Orbit-MVI does.
2. The ViewModel must extend an [`AndroidDataFlow`](https://github.com/uniflow-kt/uniflow-kt/blob/master/doc/state_action.md#updating-our-state-with-an-action) this means that if we need the `Context` in the ViewModel we cannot extends the `AndroidViewModel` but need to find a workaround.

### Image Loading
For loading images we were using [Coil](https://github.com/coil-kt/coil) because it [supports Compose](https://coil-kt.github.io/coil/compose/) out of the box and has a very nice API. Sadly there is a very bad glitch with Compose Lists (`LazyColumn`): this [issue](https://github.com/coil-kt/coil/issues/409) on coil repo describes the problem.

So we moved to [Glide](https://github.com/bumptech/glide), but glide has no support for Compose, besides from a [nasty workaround](https://github.com/bumptech/glide/issues/4459), so finally we decided to go with [Landscapist](https://github.com/skydoves/Landscapist).
