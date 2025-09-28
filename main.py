from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time

# ---------- Setup ----------
service = Service(r"C:\Users\amans\projects\Cucumberframework\chromedriver.exe")
driver = webdriver.Chrome(service=service)
driver.get("https://www.geeksforgeeks.org/ide/online-cpp-compiler")

wait = WebDriverWait(driver, 10)

# ---------- Click the language dropdown ----------
dropdown = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, ".ui.dropdown")))
dropdown.click()

# ---------- Select Java ----------
java_option = wait.until(EC.element_to_be_clickable(
    (By.XPATH, "//div[@class='item']//span[text()='Java']/parent::div")
))
java_option.click()

# ---------- Switch to the new tab ----------
original_tab = driver.current_window_handle
wait.until(lambda d: len(d.window_handles) > 1)
new_tab = [tab for tab in driver.window_handles if tab != original_tab][0]
driver.switch_to.window(new_tab)

# ---------- Focus on Ace editor via .ace_scroller ----------
editor_container = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, ".ace_scroller")))
editor_container.click()  # click the outer container to focus

# ---------- Move cursor to the end ----------
editor_container.send_keys(Keys.CONTROL, Keys.END)

# ---------- Append new code ----------
new_code = """
// Appended code
System.out.println("This is appended!");
"""

for line in new_code.strip().split("\n"):
    editor_container.send_keys(line)
    editor_container.send_keys(Keys.ENTER)

# ---------- Optional: Click Run ----------
run_button = wait.until(EC.element_to_be_clickable((By.XPATH, "//button[contains(text(), 'Run')]")))
run_button.click()

# ---------- Wait and quit ----------
time.sleep(5)
driver.quit()
