class Event < ActiveRecord::Base
	belongs_to :category
  default_scope -> { order('date ASC') }
	validates :team1, :team2, :tip, :date, :category, :odds, presence: true
	validates :odds, numericality: true
	validates :score1, :score2, numericality: { only_integer: true, allow_blank: true }

    after_create :send_gcm

    def send_gcm  
      gcm = GCM.new("AIzaSyChfJ5eadwrLa242C7xCu-Bd3_3kKUW_38")
      #registration_ids = ["cD6gVbJ8l9c:APA91bFG0AWXEbyXDRpcBmAAEFqAn1l7sX7WUUUCkGfX4ZzSgoz6_HFIPoKaJfdj83iTuQ46__sEj5crVHhKPJDwd0A55nJEy6Fc-3UvVo5asW5CPiJZBYVHutB7phZqg3ClInW6Enat", "cnTXuNrEpEY:APA91bHdX7IqCqXrAsmvUB0payy8I9UKVyca-F4QIZbsdbHMRbxLlkK789JKsBpllfJOyp4rBsFyJ_VY_RKWgV5TGmEmCsuNLOTiJnbmX9SCXHLGvd0q-K5XZVx3i5iIU0_lyj4R-z2e"]
      options = { data: {message: self.team1 + " - " + self.team2,
                      id: self.id,
                      category: self.category_id},
                collapse_key: "new_event"}

      Gcm.select(:id, :token).find_in_batches do |group|
        response = gcm.send(group.map(&:token), options)
        # p group.map(&:token)
        # p "============================================"
        # p response
        # p "=========================================="
      end
    end    
end  