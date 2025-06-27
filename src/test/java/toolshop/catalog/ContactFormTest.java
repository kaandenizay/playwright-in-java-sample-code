package toolshop.catalog;

import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import toolshop.catalog.pageobjects.ContactForm;
import toolshop.fixtures.PlaywrightBaseTest;
import toolshop.fixtures.PlaywrightBaseTestForParallel;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class ContactFormTest extends PlaywrightBaseTest {

    ContactForm contactForm;

    @DisplayName("Interacting with text fields")
    @Nested
    class WhenInteractingWithTextFields {
        @DisplayName("When submitting a request")
        @BeforeEach
        void openContactPage() {
            contactForm = new ContactForm(page);
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("Customers can use the contact form to contact us")
        @Test
        void completeForm() throws URISyntaxException {

            contactForm.setFirstName("Sarah-Jane");
            contactForm.setLastName("Smith");
            contactForm.setEmail("sarah-jane@example.com");
            contactForm.setMessage("Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!");
            contactForm.selectSubject("Warranty");
            contactForm.uploadFile("data/sample-data.txt");
            contactForm.submitForm();

            Assertions.assertThat(contactForm.getAlertMessage())
                    .contains("Thanks for your message! We will contact you shortly.");
        }

        @DisplayName("Mandatory fields")
        @ParameterizedTest
        @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
        void mandatoryFields(String fieldName) {  // injected Page parameter should be the end
            contactForm.setFirstName("Sarah-Jane");
            contactForm.setLastName("Smith");
            contactForm.setEmail("sarah-jane@example.com");
            contactForm.setMessage("Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!");
            contactForm.selectSubject("Warranty");

            contactForm.clearField(fieldName);
            contactForm.submitForm();

            // Check the error message for that field
            var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName + " is required");

            assertThat(errorMessage).isVisible();
        }

        @DisplayName("Text fields")
        @Test
        void textFieldValues() {
            var messageField = page.getByLabel("Message");

            messageField.fill("This is my message");

            assertThat(messageField).hasValue("This is my message");
        }

        @DisplayName("Dropdown lists")
        @Test
        void dropdownFieldValues() {
            var subjectField = page.getByLabel("Subject");

            subjectField.selectOption("Warranty");

            assertThat(subjectField).hasValue("warranty");
        }

        @DisplayName("File uploads")
        @Test
        void fileUploads() throws URISyntaxException {
            var attachmentField = page.getByLabel("Attachment");

            Path attachment = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());

            page.setInputFiles("#attachment", attachment);

            String uploadedFile = attachmentField.inputValue();

            org.assertj.core.api.Assertions.assertThat(uploadedFile).endsWith("sample-data.txt");
        }


        @DisplayName("By CSS class")
        @Test
        void locateTheSendButtonByCssClass() {
            page.locator("#first_name").fill("Sarah-Jane");
            page.locator(".btnSubmit").click();
            List<String> alertMessages = page.locator(".alert").allTextContents();
//            Assertions.assertTrue(!alertMessages.isEmpty());

        }

        @DisplayName("By attribute")
        @Test
        void locateTheSendButtonByAttribute() {
            page.locator("input[placeholder='Your last name *']").fill("Smith");
            assertThat(page.locator("#last_name")).hasValue("Smith");
        }
    }
}
