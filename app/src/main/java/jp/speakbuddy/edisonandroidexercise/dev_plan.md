# Core Requirements
## Persist Latest Cat Fact
- Goal: Save the latest cat fact locally so it persists between app launches.
- Implementation: Use either Room or DataStore for data persistence.
- Behavior: Upon relaunching the app, the last fetched cat fact should be displayed without fetching from the network.
## Display Fact Length
- Goal: Show the character length of a cat fact when it exceeds 100 characters.
- Implementation: Use the length field from the API response.
- Behavior: If a fact's length is greater than 100, display the length on the Home screen at the position that you feel fit.
## Indicate Multiple Cats
- Goal: Inform the user when a fact contains the word "cats".
- Implementation: Perform a simple text search for the word "cats" in the fact.
- Behavior: If the fact contains "cats", prominently display the text "Multiple cats!" on the Home screen.
## Decorate Home screen
- Goal: Add image(s) on the home screen
- Implementation: Perform Image download over network using Coil (you can use any image readily available on the internet)
- Behavior: You can show any image on the home screen that you think makes the screen more appealing to the users.
## Unit Testing
- Goal: Ensure the reliability of your code through testing.
- Implementation: Write unit tests for the ViewModel and business logic layers using fake or mockk.
- Requirements: Tests should cover happy paths and critical scenarios.


# Non Core Requirements:
- Update to toml
- Update to latest Dependencies
- Implement Hilt
- Screenshot tests?


# Implementation Plan
- Update to TOML :white_check_mark:
- Update latest dependencies :white_check_mark:
- Implement Hilt :white_check_mark:
    - Add NetworkModule :white_check_mark:
    - Update Application :white_check_mark:
    - Update Service Provider (use Hilt) :white_check_mark:
    - Update FactViewModel to use serviceProvider and enable DI :white_check_mark:
    - Inject ViewModel on Activity :white_check_mark:
- Consider moving FactResponse to separate class :white_check_mark:
- Add FactEntity :white_check_mark:
- Add FactLocalDataSource :white_check_mark:
- Add FactNetworkDataSource :white_check_mark:
- Add FactData :white_check_mark: (renamed to FactModel)
- Add FactRepository :white_check_mark:
- Update FactViewModel to use FactRepository :white_check_mark:
- Create FactDisplayMapper
    - Main Function -> Map FactData to FactDisplay Data
    - Add Fact Length (value with visibility?)
    - Add Multiple Cats (value with visibility?)
    - Add Unit Tests
- Plan about showing image