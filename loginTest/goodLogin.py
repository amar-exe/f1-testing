from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import time
from selenium import webdriver
from selenium.webdriver.common.by import By
import undetected_chromedriver as uc
import random

#update here with your username, password and search string
USERNAME = "f1@inboxkitten.com"
PASSWORD = "Test123!"


driver_path = "driver/chromedriver.exe"
options = webdriver.ChromeOptions()
options.add_argument('--ignore-certificate-errors')
options.add_argument('--ignore-ssl-errors')
driver = uc.Chrome(executable_path=driver_path, chrome_options=options)
driver.get('https://www.formula1.com/')
time.sleep(random.randint(7, 12))



try:
    cookie_button = driver.find_elements(by=By.XPATH, value='/html/body/div[5]/div/div/div[2]/div[3]/div[2]/button[2]')[0].click()
    time.sleep(random.randint(6, 10))
except:
    print("no cookie pop-up")

    


signin_element = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/header/div/div[2]/div[2]/div/div[2]/div[1]/div/a[1]')[0].click()
time.sleep(random.randint(5, 10))
username_element = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/main/div/div[3]/div/form/div[2]/input')[0]
username_element.send_keys(USERNAME)
time.sleep(random.randint(1, 4))
password_element = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/main/div/div[3]/div/form/div[3]/input')[0]
password_element.send_keys(PASSWORD)
time.sleep(random.randint(1, 4))
sign_in = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/main/div/div[3]/div/form/div[4]/button')[0].click()
time.sleep(random.randint(7, 12))
profile_button = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/header/div/div[2]/div[2]/div/div[2]/div[1]/div/a[2]')[0].click()
time.sleep(random.randint(2, 5))
logout_button = driver.find_elements(by=By.XPATH, value='/html/body/div[2]/div[3]/div/div/div/div/div/div/a[3]')[0].text
if logout_button=="LOGOUT":
    with open('output.txt', 'w') as f:
        f.write('PASSED')
else:
    with open('output.txt', 'w') as f:
        f.write('FAILED')
driver.quit()
