Feature: Product Catalog

  As as customer ,
  I want to easily search, filter, and sort products in the catalog
  So that I can find what I need quickly

  Sally is an online shopper

  Rule: Customers should be able to search for products by name
    Example: The one where Sally searches for an adjustable wrench

      Given Sally is on the home page
      When She searches for "Wrench"
      Then The "Adjustable Wrench" product should be displayed

    Example: The one where Sally searches for a more general term

      Given Sally is on the home page
      When She searches for "saw"
      Then The following products should be displayed:
        | Product      | Price  |
        | Wood Saw     | $12.18 |
        | Circular Saw | $80.19 |

    Example: The one where Sally searches for a product doesnt't exist
      Given Sally is on the home page
      When She searches for "ProductDoesNotExist"
      Then No products should be displayed
      And The message "There are no products found." should be displayed


  Rule: Customers should be able to narrow downs their search by category
    Example: The one where Sally only wants to see Hand Saws
      Given Sally is on the home page
      When She searches for "saw"
      And She filters by "Hand Saw"
      Then The following products should be displayed:
        | Product  | Price  |
        | Wood Saw | $12.18 |

    Example: The one where Sally only wants to see Power Drills
      Given Sally is on the home page
      When She searches for "drill"
      And She filters by "Power Tools"
      Then The following products should be displayed:
        | Product            | Price  |
        | Cordless Drill 24V | $66.54 |
        | Cordless Drill 12V | $46.50 |

  Rule: Customers should be able to sort products by various criteria
    Scenario Outline: Sally sorts by different criteria
      Given Sally is on the home page
      When She sorts by "<Sort>"
      Then The first product displayed should be "<First Product>"
      And  Products should be sorted correctly as "<Sort Type>"
      Examples:
        | Sort               | First Product       | Sort Type           |
        | Name (A - Z)       | Adjustable Wrench   | alphabetic          |
        | Name (Z - A)       | Wood Saw            | alphabetic-reversed |
        | Price (High - Low) | Drawer Tool Cabinet | numeric-reversed    |
        | Price (Low - High) | Washers             | numeric             |