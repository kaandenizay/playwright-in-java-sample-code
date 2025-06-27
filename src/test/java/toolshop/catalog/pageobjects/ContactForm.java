package toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContactForm {

    private final Page page;
    private final Locator firstNameField;
    private Locator lastNameField;
    private Locator emailField;
    private Locator messageField;
    private Locator subjectField;
    private Locator sendButton;

    public ContactForm(Page page) {
        this.page = page;
        this.firstNameField = page.getByLabel("First name");
        this.lastNameField = page.getByLabel("Last name");
        this.emailField = page.getByLabel("Email");
        this.messageField = page.getByLabel("Message");
        this.subjectField = page.getByLabel("Subject");
        this.sendButton = page.getByText("Send");
    }

    public void setFirstName(String firstName) {
        this.firstNameField.fill(firstName);
    }

    public void setLastName(String lastName) {
        this.lastNameField.fill(lastName);
    }

    public void setEmail(String email) {
        this.emailField.fill(email);
    }

    public void setMessage(String message) {
        this.messageField.fill(message);
    }

    public void selectSubject(String subject) {
        this.subjectField.selectOption(subject);
    }

    public void uploadFile(String filePathAndName) throws URISyntaxException {
        Path fileToUpload = Paths.get(ClassLoader.getSystemResource(filePathAndName).toURI());
        page.setInputFiles("#attachment", fileToUpload);
    }

    public void submitForm() {
        sendButton.click();
    }

    public String getAlertMessage() {
        return page.getByRole(AriaRole.ALERT).textContent().trim();
    }

    public void clearField(String fieldName) {
        page.getByLabel(fieldName).clear();
    }
}
