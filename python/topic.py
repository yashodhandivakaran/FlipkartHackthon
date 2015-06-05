WORDS_TO_SHOW = 20

class Topic:

  def __init__(self, iden, topic_str):
    self.iden = iden
    self.comps = []
    self.comps_map = {}
    self.keywords = []

    splits = topic_str.split(' + ')
    for split in splits:
      sp = split.lower().strip()
      st_splits = sp.split('*')
      word = st_splits[1]
      prob = float(st_splits[0])
      comp = (word, prob)
      self.keywords.append(word)
      self.comps.append(comp)
      self.comps_map[word] = prob

  def get_keywords(self):
    return self.keywords

  def __str__(self):
    return 'topic ' + str(self.iden) + ':' + str(self.comps[0:WORDS_TO_SHOW])
    
def build_topics(topic_strs):
  topics = []

  for idx, topic_str in enumerate(topic_strs):
    a_topic = Topic(idx, topic_str)
    topics.append(a_topic)

  return topics

def print_topics(topics):
  for a_topic in topics:
    print a_topic

def extract_keywords_from_topic(topic):
  splits = topic.split(' + ')
  key_words = []
  for split in splits:
    sp = split.lower().strip()
    st_splits = sp.split('*')
    key_words.append(st_splits[1])
  return key_words
