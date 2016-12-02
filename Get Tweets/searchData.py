import config
import tweepy
from tweepy import OAuthHandler


if __name__ == '__main__':
    auth = OAuthHandler(config.consumer_key, config.consumer_secret)
    auth.set_access_token(config.access_token, config.access_secret)
    api = tweepy.API(auth)


    for status in tweepy.Cursor(api.search, q='take',lang='en').items(100):
        with open('data/twitter_data.txt','a') as f:
            f.write('@'+status.user.name+'\n')
            f.write(status.text+'\n')
            f.write('\n')


