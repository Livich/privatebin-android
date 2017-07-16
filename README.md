# PrivateBin Android Client α

## What it provides
 * An Android client to zero knowledge paste service
 * Easy to use any PrivateBin server
 * The same security as original PrivateBin provides: no raw private data trnasferred performed
 
### To be implemented
 * Password protection support
 * File upload
 * Translations: EN, DE, UA
 * List of posted pastes
 * Rest of API implementation (paste removal and so on)
 * PrivateBin server database
 * Extended posting dialog
 * Discussion browser
 
 
 
## Technical information
 * [Retrofit 2](http://square.github.io/retrofit/) is used to communicate with PrivateBin API
 * Original [SJCL](https://github.com/bitwiseshiftleft/sjcl) library is running using [AndroidJSCore](https://github.com/ericwlange/AndroidJSCore) (much faster than Rhino)

### To be done
 * Tests
 * CI
 * PlayStore release